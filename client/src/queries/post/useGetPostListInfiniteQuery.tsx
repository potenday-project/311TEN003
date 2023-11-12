import { useInfiniteQuery } from "@tanstack/react-query";
import axios from "@/libs/axios";
import { PostInterface } from "@/types/post/PostInterface";
import { AxiosRequestConfig } from "axios";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";

export interface UseGetPostListQueryInterface extends GetPostListOptions {
  initialData?: AugmentedGetPostListResponse;
  headers?: AxiosRequestConfig["headers"];
}

export const useGetPostListInfiniteQuery = ({
  initialData,
  size,
  searchKeyword,
  headers,
}: UseGetPostListQueryInterface) => {
  return useInfiniteQuery({
    queryKey: getPostListInfiniteQueryKey.byKeyword(searchKeyword),

    queryFn: async ({ pageParam = 0 }) =>
      await getPostListQueryFn({
        page: pageParam,
        size,
        searchKeyword,
        headers: headers?.Authorization
          ? headers
          : { Authorization: getTokenFromLocalStorage() },
      }),

    getNextPageParam: ({
      currentPage,
      hasNextPage,
    }: AugmentedGetPostListResponse) =>
      hasNextPage ? currentPage + 1 : undefined,

    getPreviousPageParam: ({ currentPage }: AugmentedGetPostListResponse) =>
      currentPage > 0 ? currentPage - 1 : undefined,
    initialPageParam: 0,
    initialData: initialData
      ? { pages: [initialData], pageParams: [0] }
      : undefined,
  });
};
/**
 * 포스트리스트를 받아올 때 Query string으로 사용되는 값
 */
export interface GetPostListOptions {
  page?: number;
  size?: number;
  searchKeyword?: string;
}
/**
 * 실제 서버에서 응답해주는 값
 */
export interface GetPostListResponse {
  list: PostInterface[];
  totalCount: number;
}
/**
 * 서버응답값 + 무한스크롤을 위해 증강된 값
 */
export interface AugmentedGetPostListResponse extends GetPostListResponse {
  currentPage: number;
  hasNextPage: boolean;
}

export const getPostListQueryFn = async ({
  page = 0,
  size = 10,
  searchKeyword,
  headers,
}: GetPostListOptions & {
  headers?: AxiosRequestConfig<any>["headers"];
}): Promise<AugmentedGetPostListResponse> => {
  const { data } = await axios.get<GetPostListResponse>("/posts", {
    baseURL: process.env.NEXT_PUBLIC_BASE_URL,
    params: { page, size, searchKeyword },
    headers,
  });
  return {
    ...data,
    currentPage: page,
    hasNextPage: data.totalCount / ((page + 1) * size) > 1,
  };
};

export const getPostListInfiniteQueryKey = {
  all: ["posts"] as const,
  byKeyword: (keyword?: string) => ["posts", keyword ?? ""] as const,
};

export default useGetPostListInfiniteQuery;

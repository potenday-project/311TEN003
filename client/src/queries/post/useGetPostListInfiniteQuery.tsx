import { useInfiniteQuery, useQueryClient } from "@tanstack/react-query";
import { PostInterface } from "@/types/post/PostInterface";
import { AxiosRequestConfig } from "axios";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import { POST_LIST_V2 } from "@/const/serverPath";
import useAxiosPrivate from "@/hooks/useAxiosPrivate";
import Pagenated from "@/types/Pagenated";

export interface UseGetPostListQueryInterface extends GetPostListOptions {
  initialData?: AugmentedGetPostListResponse;
  headers?: AxiosRequestConfig["headers"];
}

export const useGetPostListInfiniteQuery = ({
  initialData,
  size,
  searchKeyword,
  searchUserNos,
  sort,
  headers,
}: UseGetPostListQueryInterface) => {
  return useInfiniteQuery({
    queryKey: getPostListInfiniteQueryKey.byKeyword({
      searchKeyword,
      searchUserNos,
      sort
    }),

    queryFn: async ({ pageParam = 0 }) =>
      await getPostListQueryFn({
        page: pageParam,
        size,
        searchKeyword,
        searchUserNos,
        sort,
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
  sort?: string;
  searchKeyword?: string;
  searchUserNos?: string;
}
/**
 * 서버응답값 + 무한스크롤을 위해 증강된 값
 */
export interface AugmentedGetPostListResponse extends Pagenated<PostInterface> {
  currentPage: number;
  hasNextPage: boolean;
}

export const getPostListQueryFn = async ({
  page = 0,
  size = 10,
  searchKeyword,
  searchUserNos,
  sort,
  headers,
}: GetPostListOptions & {
  headers?: AxiosRequestConfig<any>["headers"];
}): Promise<AugmentedGetPostListResponse> => {
  const axiosPrivate = useAxiosPrivate();
  const { data } = await axiosPrivate.get<Pagenated<PostInterface>>(
    POST_LIST_V2,
    {
      baseURL: process.env.NEXT_PUBLIC_BASE_URL,
      params: {
        page,
        size,
        searchKeyword,
        searchUserNos,
        sort: sort ?? "lastModifiedDate,desc",
      },
      headers,
    }
  );
  return {
    ...data,
    currentPage: page,
    hasNextPage: data.totalElements / ((page + 1) * size) > 1,
  };
};

// export interface PostListInfiniteQueryKey {
//   keyword?: string;
//   userNo?: string;
// }

export const getPostListInfiniteQueryKey = {
  all: ["posts"] as const,
  byKeyword: ({
    searchKeyword,
    searchUserNos,
    sort,
  }: Omit<GetPostListOptions, "page" | "size">) =>
    ["posts", { searchKeyword, searchUserNos, sort }] as const,
};

/**
 * 모든 포스트리스트 쿼리를 Invalidate 하는 Hooks
 * @returns Invalidate 함수
 */
export const useInvalidatePostList = () => {
  /**
   * 모든 포스트리스트 쿼리를 Invalidate 하는함수
   */
  const queryClinet = useQueryClient();
  return () => {
    queryClinet.invalidateQueries({
      queryKey: getPostListInfiniteQueryKey.all,
    });
  };
};

export default useGetPostListInfiniteQuery;

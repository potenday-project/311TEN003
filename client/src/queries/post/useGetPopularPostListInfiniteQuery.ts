import { useInfiniteQuery, useQueryClient } from "@tanstack/react-query";
import { PostInterface } from "@/types/post/PostInterface";
import { AxiosRequestConfig } from "axios";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import { POPULAR_POST_LIST } from "@/const/serverPath";
import useAxiosPrivate from "@/hooks/useAxiosPrivate";
import Pagenated from "@/types/Pagenated";

export interface UseGetPopularPostListQueryInterface
  extends GetPostListOptions {
  initialData?: AugmentedGetPopularPostListResponse;
  headers?: AxiosRequestConfig["headers"];
}

export const useGetPopularPostListInfiniteQuery = ({
  initialData,
  size,
  sort,
  headers,
}: UseGetPopularPostListQueryInterface) => {
  return useInfiniteQuery({
    queryKey: getPopularPostListInfiniteQueryKey.byKeyword({
      sort,
    }),

    queryFn: async ({ pageParam = 0 }) =>
      await getPopularPostListQueryFn({
        page: pageParam,
        size,
        sort,
        headers: headers?.Authorization
          ? headers
          : { Authorization: getTokenFromLocalStorage() },
      }),

    getNextPageParam: ({
      currentPage,
      hasNextPage,
    }: AugmentedGetPopularPostListResponse) =>
      hasNextPage ? currentPage + 1 : undefined,

    getPreviousPageParam: ({ currentPage }: AugmentedGetPopularPostListResponse) =>
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
}
/**
 * 서버응답값 + 무한스크롤을 위해 증강된 값
 */
export interface AugmentedGetPopularPostListResponse extends Pagenated<PostInterface> {
  currentPage: number;
  hasNextPage: boolean;
}

export const getPopularPostListQueryFn = async ({
  page = 0,
  size = 10,
  sort,
  headers,
}: GetPostListOptions & {
  headers?: AxiosRequestConfig<any>["headers"];
}): Promise<AugmentedGetPopularPostListResponse> => {
  const axiosPrivate = useAxiosPrivate();
  const { data } = await axiosPrivate.get<{ data: Pagenated<PostInterface> }>(
    POPULAR_POST_LIST,
    {
      baseURL: process.env.NEXT_PUBLIC_BASE_URL,
      params: {
        page,
        size,
        sort: sort ?? "lastModifiedDate,desc",
      },
      headers,
    }
  );
  return {
    ...data.data,
    currentPage: page,
    hasNextPage: data.data.totalElements / ((page + 1) * size) > 1,
  };
};

export interface PopularPostListInfiniteQueryKey {
  keyword?: string;
  userNo?: string;
  sort?: string;
}

export const getPopularPostListInfiniteQueryKey = {
  all: ["popular_posts"] as const,
  byKeyword: ({ sort }: Omit<GetPostListOptions, "page" | "size">) =>
    [
      "popular_posts",
      {
        sort,
      },
    ] as const,
};

/**
 * 모든 포스트리스트 쿼리를 Invalidate 하는 Hooks
 * @returns Invalidate 함수
 */
export const useInvalidatePopularPostList = () => {
  /**
   * 모든 포스트리스트 쿼리를 Invalidate 하는함수
   */
  const queryClinet = useQueryClient();
  return () => {
    queryClinet.invalidateQueries({
      queryKey: getPopularPostListInfiniteQueryKey.all,
    });
  };
};

export default useGetPopularPostListInfiniteQuery;

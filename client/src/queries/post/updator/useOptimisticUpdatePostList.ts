import { InfiniteData, useQueryClient } from "@tanstack/react-query";
import { AugmentedGetPostListResponse } from "../useGetPostListInfiniteQuery";

type QueryKey = readonly [
  "posts",
  {
    readonly searchKeyword: string | undefined;
    readonly searchUserNos: string | undefined;
    readonly sort: string | undefined;
  }
];

/**
/**
 * queryKey와 postId 를 인자로 받아 무한스크롤포스트 리스트를 업데이트 하는 함수를 리턴하는 훅
 * @param 'like' | 'unlike'
 * @returns queryKey와 postId 를 인자로 받아 무한스크롤포스트 리스트를 업데이트하는 함수
 */
export const useOptimisticUpdatePostList = ({
  type = "like",
}: {
  type: "like" | "unlike";
}) => {
  const queryClient = useQueryClient();
  /**
   * queryKey와 postId 를 인자로 받아 무한스크롤포스트 리스트를 업데이트하는 함수
   * @param queryKey 업데이트 할 쿼리 키
   * @param id 포스트 아이디
   * @returns
   */
  const updator = (queryKey: QueryKey, id: string) =>
    queryClient.setQueryData<InfiniteData<AugmentedGetPostListResponse>>(
      queryKey,
      (prev) => {
        if (!prev) {
          return;
        }
        return {
          ...prev,
          pages: prev.pages.map((page) => ({
            ...page,
            content: page.content.map((post) => {
              if (post.postNo === Number(id)) {
                return {
                  ...post,
                  likeCount:
                    type === "like" ? post.likeCount + 1 : post.likeCount - 1,
                  likedByMe: type === "like" ? true : false,
                };
              } else {
                return { ...post };
              }
            }),
          })),
        };
      }
    );
  return updator;
};

import { useQueryClient } from "@tanstack/react-query";
import { postDetailQueryKey } from "../useGetPostDetailQuery";
import { PostInterface } from "@/types/post/PostInterface";
/**
/**
 * postId 를 인자로 받아 포스트 디테일을 업데이트 하는 함수를 리턴하는 훅
 * @param 'like' | 'unlike'
 * @returns postId 를 인자로 받아 포스트 디테일을 업데이트하는 함수
 */
export const useOptimisticUpdatePostDetail = ({
  type = "like",
}: {
  type: "like" | "unlike";
}) => {
  const queryClient = useQueryClient();
  /**
   * postId 를 인자로 받아 포스트 디테일을 업데이트하는 함수
   * @param id 포스트 아이디
   * @returns
   */
  const updator = (id: string) =>
    queryClient.setQueryData<PostInterface>(
      postDetailQueryKey.byId(id),
      (prev) => {
        if (!prev) {
          return;
        }
        return {
          ...prev,
          likedByMe: type === "like" ? true : false,
          likeCount: type === "like" ? prev.likeCount + 1 : prev.likeCount - 1,
        };
      }
    );
  return updator;
};

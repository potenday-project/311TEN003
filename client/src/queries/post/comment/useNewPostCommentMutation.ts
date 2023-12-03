import { POST_COMMENT } from "@/const/serverPath";
import useAxiosPrivate from "@/hooks/useAxiosPrivate";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { commentQueryKey } from "./useGetCommentQuery";
import PostCommentListInterface from "@/types/post/PostCommentInterface";
import { useErrorHandler } from "@/utils/errorHandler";
import { useMyInfoQuery } from "@/queries/auth/useMyInfoQuery";

const useNewPostCommentMutation = (id?: string) => {
  const queryClient = useQueryClient();
  const errorHandler = useErrorHandler();
  const { data: myInfo } = useMyInfoQuery();

  return useMutation({
    mutationFn: async (content: string) => {
      if (!id) {
        return Promise.reject("id가 제공되지않음");
      } else return postComment(id, content);
    },
    onMutate: (content) => {
      queryClient.cancelQueries({ queryKey: commentQueryKey.byId(id) });

      const querySnapShot = queryClient.getQueryData<PostCommentListInterface>(
        commentQueryKey.byId(id)
      );

      queryClient.setQueryData<PostCommentListInterface>(
        commentQueryKey.byId(id),
        (prev) => {
          return {
            list: [
              {
                commentNo: Number.MAX_SAFE_INTEGER,
                commentContent: content,
                createdDate: String(new Date()),
                lastModifiedDate: String(new Date()),
                createdBy: myInfo?.nickname,
                userId: myInfo?.id,
                nickname: myInfo?.nickname,
                profileImgUrls: myInfo?.profileImages,
              },
              ...(prev?.list ?? []),
            ] as PostCommentListInterface["list"],
            totalCount: (prev?.totalCount ?? 0) + 1,
          };
        }
      );
      return { querySnapShot };
    },
    onError: (err, _queryFnParams, context) => {
      errorHandler(err);
      queryClient.setQueryData(
        commentQueryKey.byId(id),
        context?.querySnapShot
      );
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: commentQueryKey.byId(id) });
    },
  });
};
export const postComment = async (postNo: string, content: string) => {
  const axiosPrivate = useAxiosPrivate();
  const { data } = await axiosPrivate.post<{ commentNo: string }>(
    POST_COMMENT(postNo),
    { commentContent: content }
  );
  return data;
};

export default useNewPostCommentMutation;

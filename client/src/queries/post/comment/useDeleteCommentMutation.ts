import { DELETE_COMMENT } from "@/const/serverPath";
import useAxiosPrivate from "@/hooks/useAxiosPrivate";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { commentQueryKey } from "./useGetCommentQuery";
import PostCommentListInterface from "@/types/post/PostCommentInterface";
import { useErrorHandler } from "@/utils/errorHandler";

const useDeleteCommentMutation = () => {
  const queryClient = useQueryClient();
  const errorHandler = useErrorHandler();

  return useMutation({
    mutationFn: ({ postPk, commentPk }: deleteCommentInterface) =>
      deleteComment({ postPk, commentPk }),

    onMutate({ commentPk,postPk }) {
      queryClient.cancelQueries({ queryKey: commentQueryKey.byId(commentPk) });

      const querySnapShot = queryClient.getQueryData<PostCommentListInterface>(
        commentQueryKey.byId(postPk)
      );

      queryClient.setQueryData<PostCommentListInterface>(
        commentQueryKey.byId(postPk),
        (prev) => ({
          list: (prev?.list ?? []).filter(
            ({commentNo}) => String(commentNo) !== String(commentPk)
          ),
          totalCount: (prev?.totalCount ?? 0) - 1,
        })
      );
      return { querySnapShot };
    },
    onError(err, queryFnParams, context) {
      errorHandler(err);
      queryClient.setQueryData(
        commentQueryKey.byId(queryFnParams.postPk),
        context?.querySnapShot
      );
    },
    onSuccess(_data, variables) {
      queryClient.invalidateQueries({
        queryKey: commentQueryKey.byId(variables.postPk),
      });
    },
  });
};

interface deleteCommentInterface {
  postPk: string;
  commentPk: string;
}
const deleteComment = async ({ postPk, commentPk }: deleteCommentInterface) => {
  const axiosPrivate = useAxiosPrivate();
  const { data } = await axiosPrivate.delete(DELETE_COMMENT(postPk, commentPk));
  return data;
};

export default useDeleteCommentMutation;

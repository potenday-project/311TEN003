import { REMOVE_POST } from "@/const/serverPath";
import { useMutation } from "@tanstack/react-query";
import { useInvalidatePostList } from "./useGetPostListInfiniteQuery";
import { useErrorHandler } from "@/utils/errorHandler";
import useAxiosPrivate from "@/hooks/useAxiosPrivate";

export const useDeletePostMutation = () => {
  const invalidatePreviousData = useInvalidatePostList();
  const errorHandler = useErrorHandler();
  return useMutation({
    mutationFn: (pk: number) => deletePostFn(pk),
    onSuccess: () => {
      invalidatePreviousData();
    },
    onError: (err) => {
      errorHandler(err);
    },
  });
};

export const deletePostFn = (pk: number) => {
  const axiosPrivate = useAxiosPrivate();
  return axiosPrivate.delete(REMOVE_POST(pk));
};

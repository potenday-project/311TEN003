import { REMOVE_POST } from "@/const/serverPath";
import { axiosPrivate } from "@/libs/axios";
import { useMutation } from "@tanstack/react-query";
import { useInvalidatePostList } from "./useGetPostListInfiniteQuery";
import errorHandler from "@/utils/errorHandler";

export const useDeletePostMutation = () => {
  const invalidatePreviousData = useInvalidatePostList();
  return useMutation({
    mutationFn: (pk: number) => deletePostFn(pk),
    onSuccess: () => {
      invalidatePreviousData();
    },
    onError:(err)=>{
      errorHandler(err)
    }
  });
};

export const deletePostFn = (pk: number) =>
  axiosPrivate.delete(REMOVE_POST(pk));

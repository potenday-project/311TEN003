import { REMOVE_POST } from "@/const/serverPath";
import { axiosPrivate } from "@/libs/axios";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useInvalidatePostList } from "./useGetPostListInfiniteQuery";

export const useDeletePostMutation = () => {
  const invalidatePreviousData = useInvalidatePostList();
  return useMutation({
    mutationFn: (pk: number) => deletePostFn(pk),
    onSuccess: () => {
      invalidatePreviousData();
    },
  });
};

export const deletePostFn = (pk: number) =>
  axiosPrivate.delete(REMOVE_POST(pk));

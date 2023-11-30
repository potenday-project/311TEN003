import { REMOVE_FILE } from "@/const/serverPath";
import useAxiosPrivate from "@/hooks/useAxiosPrivate";
import { useErrorHandler } from "@/utils/errorHandler";
import { useMutation, useQueryClient } from "@tanstack/react-query";

const useDeleteAttachMutation = () => {
  const errorHandler = useErrorHandler();
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (attachNo: string) =>
      await deleteAttachMutationFn(attachNo),
    onError: (error) => {
      errorHandler(error);
    },
    onSuccess: () => {
      // queryClient.invalidateQueries({ queryKey: [] });
    },
  });
};

export const deleteAttachMutationFn = async (attachNo: string) => {
  const axiosPrivate = useAxiosPrivate();
  const { data } = await axiosPrivate.delete(REMOVE_FILE(attachNo));
  return data;
};

export default useDeleteAttachMutation;

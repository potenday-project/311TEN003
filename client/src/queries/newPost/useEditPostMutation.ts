import { useMutation } from "@tanstack/react-query";
import { EDIT_POST } from "@/const/serverPath";
import { NewPostRequestInterface } from "@/types/newPost/NewPostInterface";
import { useErrorHandler } from "@/utils/errorHandler";
import useAxiosPrivate from "@/hooks/useAxiosPrivate";

const useEditPostMutation = () => {
  const errorHandler = useErrorHandler();
  return useMutation({
    mutationFn: async ({
      pk,
      formData,
    }: {
      pk: string;
      formData: NewPostRequestInterface;
    }) => {
      const data = await editPostFn(pk, formData);
      return data;
    },
    onError: (err) => errorHandler(err),
  });
};

export const editPostFn = async (
  pk: string,
  formData: NewPostRequestInterface
) => {
  const axiosPrivate = useAxiosPrivate();
  const { data } = await axiosPrivate.patch(
    EDIT_POST(pk),
    formData
  );
  return data;
};

export default useEditPostMutation;

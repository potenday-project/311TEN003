import { useMutation } from "@tanstack/react-query";
import { axiosPrivate } from "@/libs/axios";
import { POST_LIST } from "@/const/serverPath";
import { NewPostRequestInterface } from "@/types/newPost/NewPostInterface";

const useNewPostMutation = () => {
  return useMutation({
    mutationFn: async (formData: NewPostRequestInterface) => {
      const data = await usePostNewPostFn(formData);
      return data;
    },
  });
};

const usePostNewPostFn = async (formData: NewPostRequestInterface) => {
  const { data } = await axiosPrivate.post<{ postNo: number }>(
    POST_LIST,
    formData
  );
  return data;
};

export default useNewPostMutation;

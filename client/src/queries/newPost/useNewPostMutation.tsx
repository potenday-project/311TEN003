import { useMutation } from "@tanstack/react-query";
import axios from "@/libs/axios";
import { POST_LIST } from "@/const/serverPath";
import { NewPostRequestInterface } from "@/types/newPost/NewPostInterface";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import { useErrorHandler } from "@/utils/errorHandler";

const useNewPostMutation = () => {
  const errorHandler = useErrorHandler();
  return useMutation({
    mutationFn: async (formData: NewPostRequestInterface) => {
      const data = await usePostNewPostFn(formData);
      return data;
    },
    onError: (err) => errorHandler(err),
  });
};

const usePostNewPostFn = async (formData: NewPostRequestInterface) => {
  const { data } = await axios.post<{ postNo: number }>(POST_LIST, formData, {
    headers: { Authorization: getTokenFromLocalStorage() },
  });
  return data;
};

export default useNewPostMutation;

import { useMutation } from "@tanstack/react-query";
import axios from "@/libs/axios";
import { POST_LIST } from "@/const/serverPath";
import { NewPostRequestInterface } from "@/types/newPost/NewPostInterface";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";

const useNewPostMutation = () => {
  return useMutation({
    mutationFn: async (formData: NewPostRequestInterface) => {
      const data = await usePostNewPostFn(formData);
      return data;
    },
  });
};

const usePostNewPostFn = async (formData: NewPostRequestInterface) => {
  const { data } = await axios.post<{ postNo: number }>(POST_LIST, formData, {
    headers: { Authorization: getTokenFromLocalStorage() },
  });
  return data;
};

export default useNewPostMutation;

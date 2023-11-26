import { useQuery } from "@tanstack/react-query";
import axios from "@/libs/axios";
import { PostInterface } from "@/types/post/PostInterface";
import { AxiosRequestConfig } from "axios";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";

interface PostdetailOption {
  initialData?: PostInterface;
  headers?: AxiosRequestConfig["headers"];
}

const useGetPostDetailQuery = (postId: string, options?: PostdetailOption) => {
  return useQuery({
    queryKey: postDetailQueryKey.byId(postId),
    queryFn: () => getPostDetailQueryFn(postId, options?.headers),
    initialData: options?.initialData,
  });
};

export const getPostDetailQueryFn = async (
  postId: string,
  options?: PostdetailOption["headers"]
) => {
  const { data } = await axios.get<PostInterface>(`/posts/${postId}`, {
    baseURL: process.env.NEXT_PUBLIC_BASE_URL,
    headers: {
      Authorization:
        options?.headers?.Authorization || getTokenFromLocalStorage(),
    },
  });
  return data;
};

export const postDetailQueryKey = {
  all: ["post"] as const,
  byId: (id: string) => ["post", id] as const,
};

export default useGetPostDetailQuery;

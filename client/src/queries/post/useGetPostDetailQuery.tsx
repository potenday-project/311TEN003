import { useSuspenseQuery } from "@tanstack/react-query";
import axios from "@/libs/axios";
import { PostInterface } from "@/types/post/PostInterface";

const useGetPostDetailQuery = (
  postId: string,
  options?: { initialData: PostInterface }
) => {
  return useSuspenseQuery({
    queryKey: ["post", postId],
    queryFn: () => getPostDetailQueryFn(postId),
    initialData: options?.initialData,
  });
};

export const getPostDetailQueryFn = async (postId: string) => {
  const { data } = await axios.get<PostInterface>(`/posts/${postId}`, {
    baseURL: process.env.NEXT_PUBLIC_BASE_URL,
  });
  return data;
};

export default useGetPostDetailQuery;

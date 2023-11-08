import { useSuspenseQuery } from "@tanstack/react-query";
import axios from "@/libs/axios";
import { PostInterface } from "@/types/post/PostInterface";

const useGetPostDetailQuery = (postId: string) => {
  return useSuspenseQuery({
    queryKey: ["post", postId],
    queryFn: () => getPostListQueryFn(postId),
    // initialData: initialData,
  });
};

export const getPostListQueryFn = async (postId: string) => {
  const { data } = await axios.get<PostInterface>(
    `/posts/${postId}`
  );
  return data;
};

export default useGetPostDetailQuery;

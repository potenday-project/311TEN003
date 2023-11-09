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
    //FIXME 수정해야함
    `/posts/${postId}`,{'baseURL':process.env.NEXT_PUBLIC_DEV_BASE_URL}
  );
  return data;
};

export default useGetPostDetailQuery;

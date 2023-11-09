import { useSuspenseQuery } from "@tanstack/react-query";
import axios from "@/libs/axios";
import { PostInterface } from "@/types/post/PostInterface";

const useGetPostListQuery = (initialData: { content: PostInterface[] }) => {
  return useSuspenseQuery({
    queryKey: ["posts"],
    queryFn: getPostListQueryFn,
    initialData: initialData,
  });
};

export const getPostListQueryFn = async () => {
  const { data } = await axios.get<{ content: PostInterface[] }>("/posts", {
    baseURL: process.env.NEXT_PUBLIC_DEV_BASE_URL,
  });
  return data;
};

export default useGetPostListQuery;

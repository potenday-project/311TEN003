import { POST_COMMENT } from "@/const/serverPath";
import axios from "@/libs/axios";
import PostCommentListInterface from "@/types/post/PostCommentInterface";
import { useSuspenseQuery } from "@tanstack/react-query";

interface CommentQueryInterface {
  postNo: string;
}

const useGetCommentQuery = ({ postNo }: CommentQueryInterface) => {
  return useSuspenseQuery({
    queryKey: commentQueryKey.byId(postNo),
    queryFn: async () => await getCommentListQueryFn(postNo),
  });
};

export const getCommentListQueryFn = async (
  id: CommentQueryInterface["postNo"]
) => {
  const { data } = await axios.get<PostCommentListInterface>(POST_COMMENT(id));
  return data;
};

export const commentQueryKey = {
  all: ["comment"] as const,
  byId: (id?: string) => ["comment", { id }] as const,
};

export default useGetCommentQuery;

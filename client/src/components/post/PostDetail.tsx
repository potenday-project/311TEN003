"use client";

import useGetPostDetailQuery from "@/queries/post/useGetPostDetailQuery";
import PostCard from "./PostCard";
import { PostInterface } from "@/types/post/PostInterface";
interface PostDetailInterface {
  postNo: string;
  initialData: PostInterface;
}
const PostDetail = async ({ postNo, initialData }: PostDetailInterface) => {
  const { data } = useGetPostDetailQuery(postNo, { initialData });
  return <PostCard {...data} />;
};
export default PostDetail;

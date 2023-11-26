"use client";

import useGetPostDetailQuery from "@/queries/post/useGetPostDetailQuery";
import PostCard from "./PostCard";
import { PostInterface } from "@/types/post/PostInterface";
import { CircularProgress } from "@mui/material";
import PostCommentList from "@/components/post/PostCommentList";
interface PostDetailInterface {
  postNo: string;
  initialData: PostInterface;
}
const PostDetail = async ({ postNo, initialData }: PostDetailInterface) => {
  const { data } = useGetPostDetailQuery(postNo, { initialData });
  //FIXME 포스트의 좋아요갯수가 업데이트 되지않음
  return data ? (
    <>
      <PostCard {...data} />
      <PostCommentList postNo={postNo}/>
    </>
  ) : (
    <CircularProgress />
  );
};
export default PostDetail;

"use client";

import useGetPostDetailQuery from "@/queries/post/useGetPostDetailQuery";
import PostCard from "@/components/post/PostCard";
import { PostInterface } from "@/types/post/PostInterface";
import PostCommentList from "@/components/post/detail/PostCommentList";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import { Suspense } from "react";
import PostCommentSkeleton from "@/components/post/detail/PostCommentSkeleton";
import PostDetailPageContext from "@/store/post/PostDetailPageContext";
import PostCommentInput from "./PostCommentInput";

export interface PostDetailInterface {
  postNo: string;
  initialData: PostInterface;
}

const PostDetail = ({ postNo, initialData }: PostDetailInterface) => {
  const { data } = useGetPostDetailQuery(postNo, {
    initialData,
    headers: { Authorization: getTokenFromLocalStorage() },
  });

  return (
    <PostDetailPageContext.Provider value={{ data }}>
      <PostCard {...data} />
      <Suspense fallback={<PostCommentSkeleton />}>
        <PostCommentList postNo={postNo} />
      </Suspense>
      <PostCommentInput/>
    </PostDetailPageContext.Provider>
  );
};
export default PostDetail;

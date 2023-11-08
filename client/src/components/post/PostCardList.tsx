"use client";

import PostCard from "@/components/post/PostCard";
import { PostInterface } from "@/types/post/PostInterface";
import useGetPostListQuery from "@/queries/post/useGetPostListQuery";

interface PostCardListProps {
  initialData: { content: PostInterface[] };
}

const PostCardList = ({ initialData }: PostCardListProps) => {
  const { data } = useGetPostListQuery(initialData);

  return (
    <>
      {data.content.map((post) => (
        <PostCard {...post} key={post.postNo} />
      ))}
    </>
  );
};

export default PostCardList;

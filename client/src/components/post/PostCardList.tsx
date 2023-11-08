"use client";
import PostCard from "@/components/post/PostCard";
import { PostInterface } from "@/types/post/PostInterface";
import axios from "@/libs/axios";
import { useEffect, useState } from "react";

const PostCardList = () => {
  const [data, setData] = useState<{ content: PostInterface[] }>();
  useEffect(() => {
    axios.get<{ content: PostInterface[] }>("/posts").then(({ data }) => {
      setData(data);
    });
  }, []);

  return (
    <>
      {data?.content.map((post) => (
        <PostCard {...post} key={post.postNo} />
      ))}
    </>
  );
};

export default PostCardList;

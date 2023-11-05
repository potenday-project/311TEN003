import { Paper } from "@mui/material";
import PostCard from "@/components/post/PostCard";
import { PostInterface } from "@/types/post/PostInterface";

const PostCardList = () => {
  const dummyPost: PostInterface[] = Array.from(new Array(5)).map((data, i) => {
    return {
      id: String(i),
      createdAt: "Mon Nov 06 2023 00:13:07",
      nickname: "testNick",
      userId: "userID",
      userImage:
        i % 2 !== 0
          ? undefined
          : "https://source.unsplash.com/random?wallpapers",
      content:
        "Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos ullam aut minus aliquam quis officia, non dolore omnis, magnam totam tenetur ad harum? Mollitia omnis odit atque blanditiis exercitationem! Voluptatum.",
      image:
        i % 2 === 0 ? [] : ["https://source.unsplash.com/random?wallpapers"],
    };
  });

  return (
    <>
      {dummyPost.map((post) => (
        <PostCard {...post} key={post.id} />
      ))}
    </>
  );
};

export default PostCardList;

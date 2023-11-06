import ModalWrapper from "@/components/ModalWrapper";
import PostDetail from "@/components/post/PostDetail";

const mockData = {
  id: "123458",
  createdAt: "Mon Nov 06 2023 00:13:07",
  nickname: "testNick",
  userId: "userID",
  userImage: "https://source.unsplash.com/random?wallpapers",
  content:
    "Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos ullam aut minus aliquam quis officia, non dolore omnis, magnam totam tenetur ad harum? Mollitia omnis odit atque blanditiis exercitationem! Voluptatum.",
  image: ["https://source.unsplash.com/random?wallpapers"],
  tags: ["해시태그1", "해시태그2"],
};

const page = () => {
  return (
    <ModalWrapper>
      <PostDetail {...mockData} />
    </ModalWrapper>
  );
};

export default page;

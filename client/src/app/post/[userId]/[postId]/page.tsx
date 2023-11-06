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

const PostDetailPage = ({
  params,
}: {
  params: { userId: string; postId: string };
}) => {
  // FIXME @로 시작되는 경우만 슬라이스 하도록 추후에 고치고, 함수화 해야함
  const parsedUserId = params.userId.slice(1, params.userId.length);
  const parsedPostId = params.postId;
  return (
    <div>
      userId:{parsedUserId}
      postId:{parsedPostId}
      <br />
      <PostDetail {...{ ...mockData, id: params.userId }} />
    </div>
  );
};

export default PostDetailPage;

import PostDetail from "@/components/post/PostDetail";
import { Suspense } from "react";

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
      <Suspense fallback={<>로딩</>}>
        <PostDetail />
      </Suspense>
    </div>
  );
};

export default PostDetailPage;

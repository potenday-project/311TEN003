import PostDetailPage from "@/app/post/[userId]/[postId]/page";
import ModalWrapper from "@/components/ModalWrapper";

const page = async ({ ...context }) => {
  const parsedPostId = context.params.postId
  return (
    <ModalWrapper disableBox>
      <PostDetailPage {...context} postNo={parsedPostId} />
    </ModalWrapper>
  );
};

export default page;

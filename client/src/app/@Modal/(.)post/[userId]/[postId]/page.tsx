import PostDetailPage from "@/app/post/[userId]/[postId]/page";
import ModalWrapper from "@/components/ModalWrapper";

const page = (props: { params: { userId: string; postId: string } }) => {
  return (
    <ModalWrapper disableBox>
      <PostDetailPage {...props} />
    </ModalWrapper>
  );
};

export default page;

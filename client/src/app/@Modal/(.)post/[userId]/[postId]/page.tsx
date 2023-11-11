import PostDetailPage from "@/app/post/[userId]/[postId]/page";
import ModalWrapper from "@/components/ModalWrapper";

const page = async ({ params }: { params: { postId: string } }) => {
  return (
    <ModalWrapper disableBox>
      <PostDetailPage params={params} />
    </ModalWrapper>
  );
};

export default page;

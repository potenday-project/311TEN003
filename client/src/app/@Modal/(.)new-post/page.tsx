import AuthProtectorlayout from "@/app/(protectedRoute)/layout";
import NewpostPage from "@/app/(protectedRoute)/new-post/page";
import ModalWrapper from "@/components/ModalWrapper";

const NewPostPage = () => {
  return (
    <AuthProtectorlayout>
      <ModalWrapper disableBox>
        <NewpostPage />
      </ModalWrapper>
    </AuthProtectorlayout>
  );
};

export default NewPostPage;

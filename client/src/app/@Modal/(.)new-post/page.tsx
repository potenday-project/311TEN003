import AuthProtectorlayout from "@/app/(protectedRoute)/layout";
import ModalWrapper from "@/components/ModalWrapper";

const NewPostPage = () => {
  return (
    <AuthProtectorlayout>
      <ModalWrapper>{"페이지"}</ModalWrapper>
    </AuthProtectorlayout>
  );
};

export default NewPostPage;

import ModalWrapper from "@/components/ModalWrapper";
import PostDetail from "@/components/post/PostDetail";
import { Suspense } from "react";

const page = () => {
  return (
    <ModalWrapper disableBox>
      <Suspense fallback={<>로딩</>}>
        <PostDetail />
      </Suspense>
    </ModalWrapper>
  );
};

export default page;

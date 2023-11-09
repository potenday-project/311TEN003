import ModalWrapper from "@/components/ModalWrapper";
import React, { ReactNode } from "react";

type Props = {
  children: ReactNode;
};

const NewPostPage = ({ children }: Props) => {
  return <ModalWrapper>{children}</ModalWrapper>;
};

export default NewPostPage;

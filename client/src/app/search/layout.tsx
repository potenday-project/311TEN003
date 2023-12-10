import CustomContainer from "@/components/layout/CustomContainer";
import React from "react";

type Props = {
  children: React.ReactNode;
};

const layout = ({ children }: Props) => {
  return <CustomContainer>{children}</CustomContainer>;
};

export default layout;

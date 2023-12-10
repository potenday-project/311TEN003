"use client";

import CustomContainer from "@/components/layout/CustomContainer";
import SignupPageContext from "@/store/auth/SignupPageContext";
import { SignupRequirement } from "@/types/auth/signupRequirement";
import { ReactNode, useState } from "react";

type layoutProps = {
  children: ReactNode;
};

const SignupLayout = ({ children }: layoutProps) => {
  const [disableBtn, setDisableBtn] = useState(false);
  const [formData, setFormData] = useState<SignupRequirement>({
    id: "",
    email: "",
    password: "",
    nickname: "",
  });
  return (
    <SignupPageContext.Provider
      value={{ formData, setFormData, disableBtn, setDisableBtn }}
    >
      <CustomContainer>{children}</CustomContainer>
    </SignupPageContext.Provider>
  );
};

export default SignupLayout;

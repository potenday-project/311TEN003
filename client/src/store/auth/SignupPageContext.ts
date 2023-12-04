import { SignupRequirement } from "@/types/auth/signupRequirement";
import { Dispatch, SetStateAction, createContext } from "react";

interface SignupPageContextInterface {
  formData: SignupRequirement;
  setFormData: Dispatch<SetStateAction<SignupRequirement>>;
  disableBtn: boolean;
  setDisableBtn: Dispatch<SetStateAction<boolean>>;
}

const SignupPageContext = createContext<SignupPageContextInterface>({
  formData: {
    id: "",
    email: "",
    password: "",
    nickname: "",
  },
  setFormData: () => {},
  disableBtn: false,
  setDisableBtn: () => {},
});

export default SignupPageContext;

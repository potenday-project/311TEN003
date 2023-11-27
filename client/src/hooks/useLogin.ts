import { LOGIN_BFF } from "@/const/serverPath";
import { axiosBff } from "@/libs/axios";
import { SigninRequirement } from "@/types/auth/signinRequirement";
import { SigninResponseInterface } from "@/types/auth/signinResponse";

/**
 * 로그인 관련 로직들이 모여있는 Hook
 * @returns login Handler
 */
export default function useLogin() {
  const loginHandler = async (props: SigninRequirement) => {
    const { id, password } = props;
    const { data } = await axiosBff.post<SigninResponseInterface>(LOGIN_BFF, {
      id,
      password,
    });
    return data;
  };

  return { loginHandler };
}

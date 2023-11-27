import { LOGIN_BFF } from "@/const/serverPath";
import axios from "@/libs/axios";
import { SigninRequirement } from "@/types/auth/signinRequirement";
import { SigninResponseInterface } from "@/types/auth/signinResponse";

/**
 * 로그인 관련 로직들이 모여있는 Hook
 * @returns login Handler
 */
export default function useLogin() {
  const loginHandler = async (props: SigninRequirement) => {
    const { id, password } = props;
    const { data } = await axios.post<SigninResponseInterface>(
      `${process.env.NEXT_PUBLIC_CLIENT_BASE_URL}${LOGIN_BFF}`,
      {
        id,
        password,
      },
      {
        baseURL: process.env.NEXT_PUBLIC_CLIENT_BASE_URL,
        withCredentials: true,
      }
    );
    return data;
  };

  return { loginHandler };
}

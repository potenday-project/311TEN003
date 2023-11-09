"use client";
import useLogin from "@/hooks/useLogin";
import { SigninRequirement } from "@/types/auth/signinRequirement";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { userInfoQueryKeys } from "./useUserInfoQuery";
import { useRouter } from "next/navigation";
import HOME from "@/const/clientPath";
import errorHandler from "@/utils/errorHandler";
import { AxiosError } from "axios";

const useLoginMutation = () => {
  const { loginHandler } = useLogin();
  const queryClient = useQueryClient();
  const router = useRouter();

  return useMutation({
    mutationKey: LoginMuataionKey.all,
    mutationFn: async ({ id, password }: SigninRequirement) =>
      await loginHandler({ id, password }),
    // 로그인에 성공한 경우, 토큰을 로컬스토리지에 저장, 이전 로그인 쿼리를 인벨리데이트
    onSuccess: async ({ token }) => {
      localStorage?.setItem("accessToken", token);
      queryClient.invalidateQueries({ queryKey: userInfoQueryKeys.all });
      router.push(HOME);
    },
    onError: (error: AxiosError<{ detailMessage: string }>) =>
      errorHandler(error.response?.data.detailMessage ?? "에러가 발생했니다"),
  });
};

/**
 *
 * @param id
 * @returns
 */
export const LoginMuataionKey = {
  /**
   * 모든 로그인 관련 키
   */
  all: ["login"] as const,
  /**
   * Id 를 기반으로 로그인뮤테이션 키를 리턴
   * @param id 유저아이디
   * @returns 로그인뮤테이션 키
   */
  byId: (id: SigninRequirement["id"]) => [...LoginMuataionKey.all, id] as const,
};
export default useLoginMutation;

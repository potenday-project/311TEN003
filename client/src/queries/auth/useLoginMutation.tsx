"use client";
import useLogin from "@/hooks/useLogin";
import { SigninRequirement } from "@/types/auth/signinRequirement";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { MyInfoQueryKeys } from "./useMyInfoQuery";
import { useRouter } from "next/navigation";
import HOME from "@/const/clientPath";
import { useErrorHandler } from "@/utils/errorHandler";
import { useGlobalLoadingStore } from "@/store/useGlobalLoadingStore";

const useLoginMutation = () => {
  const { loginHandler } = useLogin();
  const queryClient = useQueryClient();
  const router = useRouter();
  const { setLoading } = useGlobalLoadingStore();
  const errorHandler = useErrorHandler();

  return useMutation({
    mutationKey: LoginMuataionKey.all,
    mutationFn: async ({ id, password }: SigninRequirement) =>
      await loginHandler({ id, password }),
    onMutate: () => {
      setLoading(true);
    },
    // 로그인에 성공한 경우, 토큰을 로컬스토리지에 저장, 이전 로그인 쿼리를 인벨리데이트
    onSuccess: async ({ token }) => {
      localStorage?.setItem("accessToken", token);
      queryClient.invalidateQueries({ queryKey: MyInfoQueryKeys.all });
      router.push(HOME);
    },
    onError: (error) => errorHandler(error),
    onSettled: () => {
      setLoading(false);
    },
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
  byId: (id: SigninRequirement["id"]) =>
    [...LoginMuataionKey.all, { id }] as const,
};
export default useLoginMutation;

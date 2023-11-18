"use client";
import { SIGNUP_API_PATH } from "@/const/serverPath";
import axios from "@/libs/axios";
import { SignupRequirement } from "@/types/auth/signupRequirement";
import { useMutation } from "@tanstack/react-query";
import useLoginMutation from "./useLoginMutation";
import { useGlobalLoadingStore } from "@/store/useGlobalLoadingStore";

const useSignupMutation = () => {
  const { mutate: loginHandler } = useLoginMutation();
  const { setLoading } = useGlobalLoadingStore();
  return useMutation({
    mutationKey: signupMuataionKey.all,
    mutationFn: async (formData: SignupRequirement) => {
      const { id, password } = formData;
      await signupHandler(formData);
      return { id, password };
    },
    onMutate: () => {
      setLoading(true);
    },
    onSuccess: async ({ id, password }) => {
      loginHandler({ id, password });
    },
    onSettled: () => {
      setLoading(false);
    },
  });
};

export const signupHandler = async (props: SignupRequirement) => {
  const { data } = await axios.post<{ userNo: number }>(SIGNUP_API_PATH, {
    ...props,
  });
  return data;
};

export const signupMuataionKey = {
  /**
   * 모든 회원가입 관련 키
   */
  all: ["signup"] as const,
};

export default useSignupMutation;
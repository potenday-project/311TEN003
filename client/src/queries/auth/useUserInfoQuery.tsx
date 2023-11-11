"use client";
import { MY_INFO } from "@/const/serverPath";
import {axiosPrivate} from "@/libs/axios";
import { MyInfoInterface } from "@/types/auth/myInfo";
import { SigninRequirement } from "@/types/auth/signinRequirement";
import { useSuspenseQuery } from "@tanstack/react-query";

export const useUserInfoQuery = () =>
  useSuspenseQuery({
    queryKey: userInfoQueryKeys.all,
    queryFn: getMyInfoByLocalStorage,
  });

export const getMyInfoByLocalStorage = async () => {
  // const accessToken = localStorage.get("accessToken");
  const { data } = await axiosPrivate.get<MyInfoInterface>(MY_INFO, {
    // headers: { Authorization: accessToken },
  });
  return data;
};

export const userInfoQueryKeys = {
  /**
   * 모든 로그인 관련 쿼리키
   */
  all: ["me"],
  /**
   * Id 를 기반으로 로그인뮤테이션 키를 리턴
   * @param id 유저아이디
   * @returns 로그인뮤테이션 키
   */
  byId: (id: SigninRequirement["id"]) => ["me", id] as const,
};

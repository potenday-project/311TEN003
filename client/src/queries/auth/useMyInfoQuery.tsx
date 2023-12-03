"use client";
import { MY_INFO } from "@/const/serverPath";
import useAxiosPrivate from "@/hooks/useAxiosPrivate";
import { MyInfoInterface } from "@/types/auth/myInfo";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import { useQuery } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { useRouter } from "next/navigation";
import useLogoutMutation from "./useLogoutMutation";

export const useMyInfoQuery = () => {
  const router = useRouter();
  const { mutate: logout } = useLogoutMutation();

  return useQuery({
    queryKey: MyInfoQueryKeys.all,
    queryFn: async () => {
      try {
        return await getMyInfoByLocalStorage();
      } catch (err) {
        // FIXME 토큰만료체크시 로그아웃 로직 분리 필요
        if (isAxiosError(err) && err.response) {
          if (err.response.status === 401 && getTokenFromLocalStorage()) {
            logout();
          }
        }
        throw Error();
      }
    },
  });
};

export const getMyInfoByLocalStorage = async () => {
  const accessToken = getTokenFromLocalStorage();

  if (!accessToken) {
    return null;
  }
  const axiosPrivate = useAxiosPrivate();
  const { data } = await axiosPrivate.get<MyInfoInterface>(MY_INFO);
  return data;
};

export const MyInfoQueryKeys = {
  /**
   * 모든 로그인 관련 쿼리키
   */
  all: ["me"],
};

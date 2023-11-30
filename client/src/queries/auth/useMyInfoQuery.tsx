"use client";
import { MY_INFO } from "@/const/serverPath";
import useAxiosPrivate from "@/hooks/useAxiosPrivate";
import { MyInfoInterface } from "@/types/auth/myInfo";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import { useQuery } from "@tanstack/react-query";

export const useMyInfoQuery = () =>
  useQuery({
    queryKey: MyInfoQueryKeys.all,
    queryFn: getMyInfoByLocalStorage,
  });

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

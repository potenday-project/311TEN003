"use client";
import { useGlobalSnackbarStore } from "@/store/useGlobalSnackbarStore";
import { isAxiosError } from "axios";
import { useCallback } from "react";
import getTokenFromLocalStorage from "./getTokenFromLocalStorage";
import { useGlobalLoadingStore } from "@/store/useGlobalLoadingStore";

/**
 * Axios 에러의 status 코드를 판별해 적절한 토스트팝업을 표출해주는 함수를 리런하는 훅
 * @returns errorHandler (error)=>void
 */
export const useErrorHandler = () => {
  const fireToast = useGlobalSnackbarStore((state) => state.fireToast);
  const setLoading = useGlobalLoadingStore((state) => state.setLoading);
  const errorHandler = useCallback((error: Error) => {
    setLoading(false);
    if (isAxiosError(error) && error.response) {
      switch (error.response.status) {
        case 401:
          // 토큰이 만료된 경우
          if (getTokenFromLocalStorage()) {
            fireToast("세션이 만료되었습니다.");
            localStorage.removeItem("accessToken");
          } else fireToast("로그인 후 이용 가능합니다");
          return;
        case 403:
          fireToast("권한이 없습니다");
          return;
      }
    }
  }, []);
  return errorHandler;
};

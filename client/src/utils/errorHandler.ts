"use client";
import { useGlobalSnackbarStore } from "@/store/useGlobalSnackbarStore";
import { isAxiosError } from "axios";
import { useCallback } from "react";

/**
 * Axios 에러의 status 코드를 판별해 적절한 토스트팝업을 표출해주는 함수를 리런하는 훅
 * @returns errorHandler (error)=>void
 */
export const useErrorHandler = () => {
  const fireToast = useGlobalSnackbarStore((state) => state.fireToast);

  const errorHandler = useCallback((error: Error) => {
    if (isAxiosError(error) && error.response) {
      switch (error.response.status) {
        case 401:
          fireToast("로그인 후 이용 가능합니다");
      }
    }
  }, []);
  return errorHandler;
};

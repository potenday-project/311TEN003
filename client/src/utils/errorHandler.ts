"use client";
// import { useGlobalSnackbarStore } from "@/store/useGlobalSnackbarStore";
import { isAxiosError } from "axios";

export default function ErrorHandler(error: Error) {
  // const { fireToast } = useGlobalSnackbarStore();
  if (isAxiosError(error) && error.response) {
    // FIXME : Zustand 사용 연구
    // error.response.status === 401 && fireToast("로그인 후 이용 가능합니다");
    error.response.status === 401 && console.log("로그인 후 이용 가능합니다");
  }
}

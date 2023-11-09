import { SIGNIN } from "@/const/clientPath";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";
/**
 * 쿠키에 accessToken 이나 refreshToken이 없을 경우 로그인 페이지로 리다이렉트 시키는 훅스
 */
export default function useAuthProtector() {
  const cookieStore = cookies();
  const accessToken = cookieStore.get("accessToken")?.value;

  if (!accessToken) {
    redirect(SIGNIN);
  }
}

import HOME from "@/const/clientPath";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";
/**
 * 쿠키에 accessToken 이나 refreshToken이 있을경우 메인페이지로 리다이렉트 시키는 훅스
 */
export default function useLogoutProtector() {
  const cookieStore = cookies();
  const accessToken = cookieStore.get("accessToken")?.value;

  if (accessToken) {
    redirect(HOME);
  }
}

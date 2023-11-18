import { cookies } from "next/headers";

/**
 * 쿠키에서 AccessToken을 불러오는 유틸함수
 * 서버단에서만 동작
 */
export default async function getTokenFromCookies() {
  "use server";
  if (typeof window !== "undefined") {
    return;
  }
  const accessToken = cookies().get("accessToken")?.value;
  return accessToken;
}

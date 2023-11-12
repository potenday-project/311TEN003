/**
 * 로컬스토리지에서 AccessToken을 불러오는 유틸함수
 * 클라이언트 단에서만 동작
 */
export default function getTokenFromLocalStorage() {
  if (typeof window === "undefined") {
    return;
  }
  return localStorage.getItem("accessToken");
}

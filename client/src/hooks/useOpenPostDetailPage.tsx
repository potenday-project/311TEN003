import { POST_DETAIL } from "@/const/clientPath";
import { useRouter } from "next/navigation";
import { useCallback } from "react";

/**
 * userId: string, id: string 의 입력값을 같는 함수를 리턴하는 hooks
 * @returns
 */
export const useOpenPostDetailPage = () => {
  const router = useRouter();
  const openPostDetailPage = useCallback((userId: string, id: string) => {
    router.push(POST_DETAIL(userId, id));
  }, []);
  return openPostDetailPage;
};

import { POST_DETAIL } from "@/const/clientPath";
import { usePathname, useRouter } from "next/navigation";
import { useCallback } from "react";

/**
 * userId: string, id: string 의 입력값을 같는 함수를 리턴하는 hooks
 * @returns
 */
export const useOpenPostDetailPage = () => {
  const router = useRouter();
  const path = usePathname();

  const openPostDetailPage = useCallback((userId: string, id: string) => {

    if (path !== POST_DETAIL(String(userId), String(id))) {
      router.push(POST_DETAIL(String(userId), String(id)));
    }
  }, []);

  return openPostDetailPage;
};

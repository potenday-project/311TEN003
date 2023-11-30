import HOME from "@/const/clientPath";
import { LOGOUT_BFF } from "@/const/serverPath";
import { axiosBff } from "@/libs/axios";
import { useGlobalSnackbarStore } from "@/store/useGlobalSnackbarStore";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useRouter } from "next/navigation";

const useLogoutMutation = () => {
  const router = useRouter();
  const queryClient = useQueryClient();

  const fireToast = useGlobalSnackbarStore((state) => state.fireToast);

  return useMutation({
    mutationFn: logoutFn,
    onSuccess: () => {
      localStorage.removeItem("accessToken");
      queryClient.removeQueries();
      router.push(HOME);
      fireToast("로그아웃이 완료되었습니다");
    },
  });
};
const logoutFn = () => axiosBff.post(LOGOUT_BFF);

export default useLogoutMutation;

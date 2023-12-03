import { LOGOUT_BFF } from "@/const/serverPath";
import { axiosBff } from "@/libs/axios";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useRouter } from "next/navigation";

const useLogoutMutation = () => {
  const router = useRouter();
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: logoutFn,
    onSuccess: () => {
      localStorage.removeItem("accessToken");
      queryClient.removeQueries();
      router.refresh();
    },
  });
};
const logoutFn = () => axiosBff.post(LOGOUT_BFF);

export default useLogoutMutation;

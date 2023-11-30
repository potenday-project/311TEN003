import { PATCH_USER_INFO } from "@/const/serverPath";
import { UserInfoInterface } from "@/types/user/userInfoInterface";
import { useErrorHandler } from "@/utils/errorHandler";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { UserInfoQueryKey } from "./useUserInfoQuery";
import { useMyInfoQuery } from "../auth/useMyInfoQuery";
import useAxiosPrivate from "@/hooks/useAxiosPrivate";

const usePatchUserInfoMutation = () => {
  const errorHandler = useErrorHandler();
  const queryClient = useQueryClient();
  const { data: MyInfo } = useMyInfoQuery();

  return useMutation({
    mutationFn: async (info: Partial<UserInfoInterface>) =>
      patchUserInfoMutateFn(info),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: UserInfoQueryKey.byId(String(MyInfo?.userNo)),
      });
    },
    onError: (err) => errorHandler(err),
  });
};

export const patchUserInfoMutateFn = async (
  info: Partial<UserInfoInterface>
) => {
  const axiosPrivate = useAxiosPrivate();
  const { data } = await axiosPrivate.patch(PATCH_USER_INFO, { ...info });
  return data;
};

export default usePatchUserInfoMutation;

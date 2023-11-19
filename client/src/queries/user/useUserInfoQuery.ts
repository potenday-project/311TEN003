import { USER_SUMMARY } from "@/const/serverPath";
import axios from "@/libs/axios";
import { UserInfoInterface } from "@/types/user/userInfoInterface";
import { useQuery } from "@tanstack/react-query";
import { AxiosRequestConfig } from "axios";

const useUserInfoQuery = ({
  userId,
  initialData,
  config,
}: {
  userId: string;
  initialData?: UserInfoInterface;
  config?: AxiosRequestConfig<any>;
}) =>
  useQuery({
    queryKey: UserInfoQueryKey.byId(userId),
    queryFn: async () => await getUserInfoFn(userId, config),
    initialData,
  });

export const getUserInfoFn = async (
  userId: string,
  config?: AxiosRequestConfig<any>
) => {
  const { data } = await axios.get<UserInfoInterface>(
    USER_SUMMARY(userId),
    config
  );
  return data;
};
export const UserInfoQueryKey = {
  all: ["userInfo"] as const,
  byId: (userId: string) => ["userInfo", { userId }] as const,
};

export default useUserInfoQuery;

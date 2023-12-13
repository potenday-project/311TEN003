import { FOLLOW_USER } from "@/const/serverPath";
import axios from "@/libs/axios";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { UserInfoQueryKey } from "./useUserInfoQuery";
import { UserInfoInterface } from "@/types/user/userInfoInterface";
import { MyInfoQueryKeys } from "../auth/useMyInfoQuery";
import { MyInfoInterface } from "@/types/auth/myInfo";
import { useErrorHandler } from "@/utils/errorHandler";
import { followerUserQueryKey } from "./useFollowerUserInfiniteQuery";

const useFollowMutation = () => {
  const queryClient = useQueryClient();
  const errorHandler = useErrorHandler();
  return useMutation({
    mutationFn: async (userNo: string) => await followUserMutatuibFn(userNo),
    /**
     * 팔로우시 결과에 관계없이 선제적으로 해당유저의 팔로워를 변화시키고, 팔로잉 여부를 True로 설정
     * @param userNo 팔로우할 유저 PK
     */
    onMutate: (userNo: string) => {
      const queryKey = UserInfoQueryKey.byId(userNo);
      // 진행중인 쿼리를 캔슬
      queryClient.cancelQueries({ queryKey });
      // 쿼리 스냅샷 생성
      const querySnapshot =
        queryClient.getQueryData<UserInfoInterface>(queryKey);
      // Optimastic Update
      queryClient.setQueryData<UserInfoInterface>(queryKey, (prev) => {
        if (!prev) {
          return;
        }
        return {
          ...prev,
          isFollowing: true,
          followerCount: prev.followerCount + 1,
        };
      });
      // onError에서 사용할 쿼리스냅샷 리턴
      return { querySnapshot };
    },
    /**
     * Mutation 실패시 원래 QuerySnapShot정보로 롤백
     */
    onError: (err, queryFnParams, context) => {
      errorHandler(err);
      if (!context) {
        return;
      }
      queryClient.setQueryData(
        UserInfoQueryKey.byId(queryFnParams),
        context?.querySnapshot
      );
    },
    onSuccess: () => {
      const userInfo = queryClient.getQueryData<MyInfoInterface>(
        MyInfoQueryKeys.all
      );
      userInfo &&
        queryClient.invalidateQueries({
          queryKey: UserInfoQueryKey.byId(userInfo?.userNo),
        });
      // TODO 낙관적업데이트 구현
      queryClient.invalidateQueries({ queryKey: followerUserQueryKey.all });
    },
  });
};

export const followUserMutatuibFn = async (userNo: string) => {
  const token = getTokenFromLocalStorage();
  const { data } = await axios.post<{ userFollowNo: number }>(
    FOLLOW_USER(userNo),
    null,
    {
      headers: { Authorization: token },
    }
  );
  return data;
};

export default useFollowMutation;

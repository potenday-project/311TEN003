"use client";
import UserAvatar from "@/components/user/info/UserAvatar";
import FollowUserBtn from "@/components/user/info/FollowUserBtn";
import { Box, Button, Stack, Typography } from "@mui/material";
import { UserInfoInterface } from "@/types/user/userInfoInterface";
import useUserInfoQuery from "@/queries/user/useUserInfoQuery";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import { useMyInfoQuery } from "@/queries/auth/useMyInfoQuery";
import { useContext, useMemo } from "react";
import UserPageContext from "@/store/user/UserPageContext";
import UserInfoCardSkeleton from "./UserInfoCardSkeleton";
import { useRouter } from "next/navigation";
import { USER_FOLLOW_LIST } from "@/const/clientPath";

type Props = {
  initialData?: UserInfoInterface;
  userId: string;
};

const UserInfo = ({ initialData, userId }: Props) => {
  const { data: userInfo } = useMyInfoQuery();

  const isMyProfile = useMemo(
    () => String(userInfo?.userNo) === String(userId),
    [userInfo, userId]
  );

  const token = getTokenFromLocalStorage();
  const router = useRouter();
  const { setIsEditing } = useContext(UserPageContext);

  const { data } = useUserInfoQuery({
    userId,
    initialData,
    config: { headers: { Authorization: token } },
  });

  if (!data) {
    return <UserInfoCardSkeleton />;
  }

  const {
    id,
    followerCount,
    followingCount,
    nickname,
    profileImages,
    isFollowing,
    introduction,
  } = data;

  return (
    <Stack alignItems="center" gap={1}>
      <UserAvatar
        src={profileImages[0]?.attachUrl}
        fallback={id}
        sx={{ width: "56px", height: "56px" }}
      />
      <Stack direction="row" gap={1}>
        <Typography color="primary.main" fontWeight="bold">
          {nickname}
        </Typography>
        <Typography color="text.secondary">@{id}</Typography>
      </Stack>
      <Box sx={{ height: 48 }}>
        <Typography color="text.secondary">
          {introduction ?? "자기소개가 없습니다"}
        </Typography>
      </Box>
      <Stack
        direction="row"
        gap={1}
        sx={{cursor:'pointer'}}
        onClick={() => {
          isMyProfile && router.push(USER_FOLLOW_LIST);
        }}
      >
        <Typography fontWeight="bold">{followerCount}</Typography>
        <Typography color="text.secondary">팔로워</Typography>
        <Typography fontWeight="bold">{followingCount}</Typography>
        <Typography color="text.secondary">팔로잉</Typography>
      </Stack>
      {isMyProfile ? (
        <Button
          onClick={() => setIsEditing(true)}
          sx={{
            backgroundColor: "#F6EAFB",
            color: "primary.main",
            ":hover": {
              backgroundColor: "#F6EAFB",
            },
          }}
          fullWidth
        >
          프로필 수정하기
        </Button>
      ) : (
        <FollowUserBtn userId={userId} isFollowing={isFollowing} fullWidth />
      )}
    </Stack>
  );
};

export default UserInfo;

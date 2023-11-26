"use client";
import UserAvatar from "@/components/user/info/UserAvatar";
import FollowUserBtn from "@/components/user/info/FollowUserBtn";
import { Box, Button, Stack, Typography } from "@mui/material";
import { UserInfoInterface } from "@/types/user/userInfoInterface";
import useUserInfoQuery from "@/queries/user/useUserInfoQuery";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import { useMyInfoQuery } from "@/queries/auth/useMyInfoQuery";
import { useMemo } from "react";

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

  const { data } = useUserInfoQuery({
    userId,
    initialData,
    config: { headers: { Authorization: token } },
  });
  if (!data) {
    return <></>;
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
      <Stack direction="row" gap={1}>
        <Typography fontWeight="bold">{followerCount}</Typography>
        <Typography color="text.secondary">팔로워</Typography>
        <Typography fontWeight="bold">{followingCount}</Typography>
        <Typography color="text.secondary">팔로잉</Typography>
      </Stack>
      {isMyProfile ? (
        <Button fullWidth>설정</Button>
      ) : (
        <FollowUserBtn userId={userId} isFollowing={isFollowing} fullWidth />
      )}
    </Stack>
  );
};

export default UserInfo;

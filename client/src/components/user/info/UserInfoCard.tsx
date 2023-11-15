import { MyInfoInterface } from "@/types/auth/myInfo";
import React from "react";
import UserAvatar from "@/components/user/info/UserAvatar";
import { Box, Button, Typography } from "@mui/material";

type Props = {
  data: MyInfoInterface & { isFollowing: boolean };
};

const UserInfo = ({ data }: Props) => {
  
  const {
    id,
    followerCount,
    nickname,
    profileImages,
    isFollowing,
    introduction,
  } = data;

  return (
    <Box sx={{display:'flex',alignItems:'center',flexDirection:'column', gap:1}}>
      <UserAvatar
        src={profileImages[0]?.attachUrl}
        fallback={id}
        sx={{width:"56px",height:'56px'}}
      />
      <Box sx={RowWrapperSX}>
        <Typography color="primary.main" fontWeight="bold">
          {nickname}
        </Typography>
        <Typography color="text.secondary">@{id}</Typography>
      </Box>
      <Box sx={{height:48}}>
      <Typography color="text.secondary">
        {introduction ?? "자기소개가 없습니다"}
      </Typography></Box>
      <Box sx={RowWrapperSX}>
        <Typography fontWeight="bold">{followerCount}</Typography>
        <Typography color="text.secondary">팔로워</Typography>
      </Box>
      {isFollowing ? (
        <Button variant="outlined" fullWidth>
          언팔로우
        </Button>
      ) : (
        <Button fullWidth>팔로우</Button>
      )}
    </Box>
  );
};

const RowWrapperSX = {
  display: "flex",
  gap: 1,
};

export default UserInfo;

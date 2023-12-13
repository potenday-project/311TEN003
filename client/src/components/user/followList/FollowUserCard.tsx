import { Button, Stack, Typography } from "@mui/material";
import React from "react";
import UserAvatar from "@/components/user/info/UserAvatar";
import { useRouter } from "next/navigation";
import { USER_PAGE } from "@/const/clientPath";
import useUnFollowMutation from "@/queries/user/useUnFollowMutation";

type Props = {
  imageUrl?: string;
  nickName: string;
  userId: string;
  content: string;
  userPk: number;
};

const FollowUserCard = ({
  userPk,
  imageUrl,
  nickName,
  userId,
  content,
}: Props) => {
  const router = useRouter();
  const { mutate: unfollowHandler } = useUnFollowMutation();

  return (
    <Stack direction="row" gap={1} py={1}>
      <UserAvatar
        src={imageUrl}
        alt={`${nickName}의 프로필`}
        fallback={nickName}
        onClick={() => router.push(USER_PAGE(userPk))}
        sx={{ cursor: "pointer" }}
      />
      <Stack gap={1} flexGrow={1}>
        <Stack direction="row" justifyContent={"space-between"}>
          <Stack
            sx={{ cursor: "pointer" }}
            onClick={() => router.push(USER_PAGE(userPk))}
          >
            <Typography fontSize="caption1" color="text.main">
              {nickName}
            </Typography>
            <Typography fontSize="caption2" color="text.secondary">
              @{userId}
            </Typography>
          </Stack>
          <Button
            variant="outlined"
            onClick={() => unfollowHandler(String(userPk))}
          >
            언팔로우
          </Button>
        </Stack>
        <Typography>{content}</Typography>
      </Stack>
    </Stack>
  );
};

export default FollowUserCard;

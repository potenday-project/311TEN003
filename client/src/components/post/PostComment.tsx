import { USER_PAGE } from "@/const/clientPath";
import { Stack, Avatar, Typography } from "@mui/material";
import dayjs from "dayjs";
import Link from "next/link";

type Props = {
  content: string;
  nickname: string;
  userId: string;
  userPk: string;
  createdAt: string;
};

const PostComment = ({
  content,
  nickname,
  userId,
  createdAt,
  userPk,
}: Props) => {
  return (
    <Stack direction="row" gap={1.25}>
      <Avatar />
      <Stack gap={1}>
        <Stack direction="row" gap={1}>
          <Link href={USER_PAGE(userPk)}>
            <Typography>{nickname}</Typography>
          </Link>
          <Link href={USER_PAGE(userPk)}>
            <Typography
              variant="label"
              color={"text.secondary"}
            >{`@${userId}`}</Typography>
          </Link>
          <Typography variant="label" color={"text.secondary"}>
            {dayjs(createdAt).format("MM.DD")}
          </Typography>
        </Stack>
        <Typography>{content}</Typography>
      </Stack>
    </Stack>
  );
};

export default PostComment;

import UserAvatar from "@/components/user/info/UserAvatar";
import { USER_PAGE } from "@/const/clientPath";
import { useMyInfoQuery } from "@/queries/auth/useMyInfoQuery";
import { Stack, Avatar, Typography } from "@mui/material";
import dayjs from "dayjs";
import Link from "next/link";
import PostCommentDropdown from "./PostCommentDropdown";
import useDeleteCommentMutation from "@/queries/post/comment/useDeleteCommentMutation";

type Props = {
  content: string;
  nickname: string;
  userId: string;
  userPk: string;
  profileImg?: string;
  createdAt: string;
  commentPk: string;
  postPk: string;
};

const PostComment = ({
  content,
  nickname,
  userId,
  createdAt,
  profileImg,
  userPk,
  postPk,
  commentPk,
}: Props) => {
  const { data: myData } = useMyInfoQuery();

  const isMyComment = userPk === String(myData?.userNo);
  const { mutateAsync: onDelete } = useDeleteCommentMutation();

  return (
    <Stack direction="row" width="100%" gap={1.25}>
      <UserAvatar src={profileImg} fallback={nickname} />
      <Stack gap={1} flexGrow={1}>
        <Stack
          direction="row"
          justifyContent="space-between"
          sx={{ height: 24 }}
        >
          <Stack direction="row" gap={1}>
            <Link href={USER_PAGE(userPk)}>
              <Typography component="span">{nickname}</Typography>
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
          {isMyComment && (
            <PostCommentDropdown
              onDelete={() => {
                onDelete({
                  commentPk: String(commentPk),
                  postPk: String(postPk),
                });
              }}
              onEdit={() => {}}
            />
          )}
        </Stack>
        <Typography>{content}</Typography>
      </Stack>
    </Stack>
  );
};

export default PostComment;

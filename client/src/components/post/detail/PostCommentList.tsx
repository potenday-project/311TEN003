import useGetCommentQuery from "@/queries/post/comment/useGetCommentQuery";
import PostComment from "./PostComment";
import { Card, Stack } from "@mui/material";

type Props = {
  postNo: string;
};

const PostCommentList = ({ postNo }: Props) => {
  const { data: comments } = useGetCommentQuery({ postNo });

  return comments.list.length > 0 ? (
    <Card sx={{ display: "flex", gap: 2, p: 2 }}>
      <Stack gap={2} width="100%">
        {comments.list.map(
          (
            {
              commentNo,
              commentContent,
              createdDate,
              createdBy,
              nickname,
              userId,
              profileImgUrls,
            },
            i
          ) => (
            <PostComment
              content={commentContent}
              createdAt={createdDate}
              nickname={nickname}
              profileImg={profileImgUrls?.[0]?.attachUrl}
              userId={userId}
              userPk={String(createdBy)}
              key={commentNo}
            />
          )
        )}
      </Stack>
    </Card>
  ) : (
    <></>
  );
};

export default PostCommentList;

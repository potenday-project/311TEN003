import PostComment from "./PostComment";
import { Card, Stack } from "@mui/material";

type Props = {
  postNo: string;
};

const PostCommentList = ({ postNo }: Props) => {
  const comments = Array.from(new Array(4)).map((_e, i) => ({
    commentNo: i,
    comment:
      "Lorem ipsum dolor sit amet consectetur adipisicing elit. Repellendus laudantium enim veritatis minus excepturi. Quidem iusto, velit facilis corporis at suscipit minima sed, cumque nostrum fugiat ad natus? Voluptatum, odio?",
    createdAt: new Date(),
    nickname: "유저이름",
    userId: "유저유저아이디",
  }));
  return (
    comments &&
    comments.length > 0 && (
      <Card sx={{ display: "flex", gap: 2, p: 2 }}>
        <Stack gap={2}>
          {comments.map(
            ({ commentNo, comment, createdAt, nickname, userId }, i) => (
              <PostComment
                content={comment}
                createdAt={String(createdAt)}
                nickname={nickname}
                userId={userId}
                userPk={String(i)}
                key={commentNo}
              />
            )
          )}
        </Stack>
      </Card>
    )
  );
};

export default PostCommentList;

import { PostInterface } from "@/types/post/PostInterface";
import {
  Avatar,
  ButtonBase,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  CardMedia,
  TextField,
  Typography,
} from "@mui/material";
import PostHashTagList from "./PostHashtagList";

const PostDetail = ({
  image,
  createdAt,
  userId,
  nickname,
  content,
  userImage,
  tags,
  id,
}: PostInterface) => {
  return (
    <Card sx={{ p: 4 }}>
      <CardHeader
        avatar={
          <Avatar
            sx={{ bgcolor: "secondary.main" }}
            src={userImage}
            data-testid="avatar"
          >
            {userImage || userId[0].toUpperCase()}
          </Avatar>
        }
        title={`${userId} ${nickname}`}
        subheader={createdAt}
        sx={{ p: 0 }}
      />
      <CardContent sx={{ px: 0 }}>
        <Typography variant="body1">{content}</Typography>
        <PostHashTagList tags={tags} />
      </CardContent>
      {/* 이미지 */}
      {image.length !== 0 && (
        <CardMedia
          data-testid="postImg"
          component="img"
          height="360px"
          image={image[0]}
          alt={`${userId}의 포스트`}
          sx={{ borderRadius: 2, bgcolor: "background.default" }}
        />
      )}
      <CardActions sx={{ justifyContent: "end", gap: 2, px: 0 }}>
        <ButtonBase data-testid="likeBtn" aria-label="add to favorites">
          <Typography>좋아요</Typography>
        </ButtonBase>
        <ButtonBase data-testid="shareBtn" aria-label="share">
          <Typography>공유하기</Typography>
        </ButtonBase>
      </CardActions>
      <TextField label="댓글" fullWidth></TextField>
    </Card>
  );
};
export default PostDetail;

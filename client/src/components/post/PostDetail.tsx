// FIXME 실제 서버연결시 바꿔야함
"use client";
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
import { FavoriteBorder, ShareOutlined } from "@mui/icons-material";
import useGetPostDetailQuery from "@/queries/post/useGetPostDetailQuery";

const PostDetail = () => {
  const { data } = useGetPostDetailQuery('1');
  const {
    postAttachUrl,
    createdAt,
    id,
    nickname,
    postContent,
    tagList,
    likeCount,
    profileImgUrls,
  }: PostInterface = data
  return (
    <Card sx={{ p: 4 }}>
      <CardHeader
        avatar={
          <Avatar
            sx={{ bgcolor: "secondary.main" }}
            src={profileImgUrls}
            data-testid="avatar"
          >
            {profileImgUrls || id[0].toUpperCase()}
          </Avatar>
        }
        title={`${id} ${nickname}`}
        subheader={createdAt}
        sx={{ p: 0 }}
      />
      <CardContent sx={{ px: 0 }}>
        <Typography variant="body1">{postContent}</Typography>
        <PostHashTagList tags={tagList} />
      </CardContent>
      {/* 이미지 */}
      {postAttachUrl.length !== 0 && (
        <CardMedia
          data-testid="postImg"
          component="img"
          height="360px"
          image={postAttachUrl[0]}
          alt={`${id}의 포스트`}
          sx={{ borderRadius: 2, bgcolor: "background.default" }}
        />
      )}
      <CardActions sx={{ justifyContent: "end", gap: 2, px: 0 }}>
        <ButtonBase data-testid="likeBtn" aria-label="add to favorites">
          <FavoriteBorder />
          <Typography>{likeCount}</Typography>
        </ButtonBase>
        <ButtonBase data-testid="shareBtn" aria-label="share">
          <ShareOutlined />
          <Typography>공유하기</Typography>
        </ButtonBase>
      </CardActions>
      <TextField label="댓글" fullWidth></TextField>
    </Card>
  );
};
export default PostDetail;

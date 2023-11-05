"use client";
import { PostInterface } from "@/types/post/PostInterface";
import {
  FavoriteOutlined,
  MoreVertOutlined,
  ShareOutlined,
} from "@mui/icons-material";
import {
  Avatar,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  CardMedia,
  IconButton,
  Typography,
} from "@mui/material";

const PostCard = ({
  image,
  createdAt,
  userId,
  nickname,
  content,
  userImage,
}: PostInterface) => {
  return (
    <Card>
      {/* Header */}
      <CardHeader
        data-testid="mui-header"
        avatar={
          <Avatar
            sx={{ bgcolor: "secondary.main" }}
            src={userImage}
            data-testid="avatar"
          >
            {userImage || userId[0].toUpperCase()}
          </Avatar>
        }
        action={
          <IconButton aria-label="settings">
            <MoreVertOutlined />
          </IconButton>
        }
        title={`@${userId}`}
        subheader={nickname}
      />
      {/* image */}
      {image.length !== 0 && (
        <CardMedia
          data-testid="postImg"
          component="img"
          height="360"
          image={image[0]}
          alt={`${userId}의 포스트`}
        />
      )}
      {/* Contents */}
      <CardContent>
        <Typography variant="body1" color="text.secondary">
          {content}
        </Typography>
      </CardContent>
      {/* CTA */}
      <CardActions disableSpacing>
        <IconButton aria-label="add to favorites">
          <FavoriteOutlined data-testid="likeBtn" />
        </IconButton>
        <IconButton data-testid="shareBtn" aria-label="share">
          <ShareOutlined />
        </IconButton>
      </CardActions>
    </Card>
  );
};

export default PostCard;

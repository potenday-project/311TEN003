"use client";

import { PostInterface } from "@/types/post/PostInterface";

import { MoreVertOutlined } from "@mui/icons-material";
import {
  Avatar,
  Box,
  Card,
  CardActions,
  CardContent,
  CardMedia,
  Typography,
  ButtonBase,
} from "@mui/material";
import PostHashTagList from "./PostHashtagList";
import { useOpenPostDetailPage } from "@/hooks/useOpenPostDetailPage";
import { useMemo } from "react";

const PostCard = ({
  image,
  createdAt,
  userId,
  nickname,
  content,
  userImage,
  tags,
  id,
}: PostInterface) => {
  const openPostDetailPage = useOpenPostDetailPage();
  const hasImage = useMemo(() => image.length !== 0, [image]);

  return (
    <Card sx={{ display: "flex", gap: 2, p: 2 }}>
      <Avatar
        sx={{ bgcolor: "secondary.main" }}
        sizes="40"
        src={userImage}
        data-testid="avatar"
      >
        {userImage || userId[0].toUpperCase()}
      </Avatar>
      <Box>
        {/* Header */}
        <Box
          data-testid="mui-header"
          sx={{
            display: "flex",
            flexDirection: "row",
            justifyContent: "space-between",
            px: 0,
            width: "100%",
          }}
        >
          <Box
            sx={{
              display: "flex",
              flexDirection: "row",
              gap: 1,
              alighItems: "center",
            }}
          >
            {/* 타이틀 */}
            <Typography sx={{ fontWeight: "bold" }}>{nickname}</Typography>
            <Typography>{`@${userId}`}</Typography>
            <Typography>{createdAt}</Typography>
          </Box>

          <ButtonBase aria-label="settings" sx={{ p: 0 }}>
            <MoreVertOutlined />
          </ButtonBase>
        </Box>

        <CardContent sx={{ px: 0 }}>
          {/* Contents */}
          <Typography
            variant="body1"
            className={hasImage ? "line-clamp-2" : "line-clamp-5"}
            onClick={() => openPostDetailPage(userId, id)}
          >
            {content}
          </Typography>

          {/* Hash tags */}
          <PostHashTagList tags={tags} />
        </CardContent>

        {/* image */}
        {hasImage && (
          <CardMedia
            data-testid="postImg"
            component="img"
            height="142"
            onClick={() => openPostDetailPage(userId, id)}
            image={image[0]}
            alt={`${userId}의 포스트`}
            sx={{ borderRadius: 2, bgcolor: "background.default" }}
          />
        )}
        {/* CTA */}
        <CardActions sx={{ px: 0, justifyContent: "end", gap: 2 }}>
          <ButtonBase data-testid="commentBtn" aria-label="share">
            <Typography onClick={() => openPostDetailPage(userId, id)}>
              댓글
            </Typography>
          </ButtonBase>
          <ButtonBase data-testid="likeBtn" aria-label="add to favorites">
            <Typography>좋아요</Typography>
          </ButtonBase>
          <ButtonBase data-testid="shareBtn" aria-label="share">
            <Typography>공유하기</Typography>
          </ButtonBase>
        </CardActions>
      </Box>
    </Card>
  );
};

export default PostCard;

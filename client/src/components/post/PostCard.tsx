"use client";

import { PostInterface } from "@/types/post/PostInterface";

import {
  FavoriteBorder,
  ModeCommentOutlined,
  MoreVertOutlined,
  ShareOutlined,
} from "@mui/icons-material";
import {
  Avatar,
  Box,
  Card,
  CardActions,
  CardContent,
  CardMedia,
  Typography,
  ButtonBase,
  Button,
} from "@mui/material";
import PostHashTagList from "./PostHashtagList";
import { useOpenPostDetailPage } from "@/hooks/useOpenPostDetailPage";
import { useMemo } from "react";

const PostCard = ({
  postAttachUrl,
  createdAt,
  id,
  nickname,
  postContent,
  tagList,
  postNo,
  likeCount,
}: PostInterface) => {
  const openPostDetailPage = useOpenPostDetailPage();
  // FIXME 유저이미지가 오지 않음
  const userImage = undefined;
  const hasImage = useMemo(() => postAttachUrl.length !== 0, [postAttachUrl]);

  return (
    <Card sx={{ display: "flex", gap: 2, p: 2 }}>
      <Avatar
        sx={{ bgcolor: "secondary.main" }}
        sizes="40"
        src={userImage}
        data-testid="avatar"
      >
        {userImage || id[0].toUpperCase()}
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
            <Typography>{`@${id}`}</Typography>
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
            onClick={() => openPostDetailPage(id, String(postNo))}
          >
            {postContent}
          </Typography>

          {/* Hash tags */}
          <PostHashTagList tags={tagList} />
        </CardContent>

        {/* image */}
        {hasImage && (
          <CardMedia
            data-testid="postImg"
            component="img"
            height="142"
            onClick={() => openPostDetailPage(id, id)}
            image={postAttachUrl[0]}
            alt={`${id}의 포스트`}
            sx={{ borderRadius: 2, bgcolor: "background.default" }}
          />
        )}
        {/* CTA */}
        <CardActions sx={{ px: 0, justifyContent: "end", gap: 2 }}>
          <ButtonBase data-testid="commentBtn" aria-label="share">
            <ModeCommentOutlined />
            <Typography onClick={() => openPostDetailPage(id, String(postNo))}>
              댓글수
            </Typography>
          </ButtonBase>
          <ButtonBase data-testid="likeBtn" aria-label="add to favorites">
            <FavoriteBorder />
            <Typography>{likeCount}</Typography>
          </ButtonBase>
          <ButtonBase data-testid="shareBtn" aria-label="share">
            <ShareOutlined />
            <Typography>공유하기</Typography>
          </ButtonBase>
        </CardActions>
      </Box>
    </Card>
  );
};

export default PostCard;

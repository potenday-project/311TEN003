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
import ShareIcon from "@/assets/icons/ShareIcon.svg";
import LikeIcon from "@/assets/icons/LikeIcon.svg";
import CommentIcon from "@/assets/icons/CommentIcon.svg";
import QuoteIcon from "@/assets/icons/QuoteIcon.svg";
import AlcoleNameTag from "./AlcoleNameTag";
import dayjs from "dayjs";

const PostCard = ({
  postAttachUrls,
  id,
  nickname,
  postContent,
  updateDt,
  tagList,
  postNo,
  likeCount,
  profileImgUrls,
  alcoholName,
  alcoholType,
  commentCount,
}: PostInterface) => {
  const openPostDetailPage = useOpenPostDetailPage();
  const hasImage = useMemo(() => postAttachUrls.length !== 0, [postAttachUrls]);

  return (
    <Card sx={{ display: "flex", gap: 2, p: 2 }}>
      <Avatar
        sx={{ bgcolor: "secondary.main" }}
        sizes="40"
        src={profileImgUrls[0]}
        data-testid="avatar"
      >
        {profileImgUrls[0] || String(id)[0].toUpperCase()}
      </Avatar>
      <Box sx={{ width: "100%" }}>
        {/* Header */}
        <Box
          data-testid="mui-header"
          sx={{
            display: "flex",
            flexDirection: "row",
            justifyContent: "space-between",
            px: 0,
          }}
        >
          <Box
            sx={{
              display: "flex",
              flexDirection: "row",
              gap: 1,
            }}
          >
            {/* 타이틀 */}
            <Typography color="primary.main">{nickname}</Typography>
            <Typography
              variant="label"
              color={"text.secondary"}
            >{`@${id}`}</Typography>
            <Typography variant="label" color={"text.secondary"}>
              {dayjs(updateDt).format("MM.DD")}
            </Typography>
          </Box>

          <ButtonBase aria-label="settings" sx={{ p: 0 }}>
            <MoreVertOutlined />
          </ButtonBase>
        </Box>
        <AlcoleNameTag alcoholName={alcoholName} alcoholType={alcoholType} />

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
            image={postAttachUrls[0]}
            alt={`${id}의 포스트`}
            sx={{ borderRadius: 2, bgcolor: "background.default" }}
          />
        )}
        {/* CTA */}
        <CardActions sx={{ px: 0, justifyContent: "end", gap: 2 }}>
          <ButtonBase data-testid="commentBtn" aria-label="comment">
            <CommentIcon />
            <Typography
              variant="label"
              onClick={() => openPostDetailPage(id, String(postNo))}
            >
              {commentCount ?? 0}
            </Typography>
          </ButtonBase>
          <ButtonBase data-testid="likeBtn" aria-label="like">
            <LikeIcon />
            <Typography variant="label">{likeCount ?? 0}</Typography>
          </ButtonBase>
          <ButtonBase data-testid="QuoteBtn" aria-label="Quote">
            <QuoteIcon />
            <Typography variant="label">인용</Typography>
          </ButtonBase>
          <ButtonBase data-testid="shareBtn" aria-label="share">
            <ShareIcon />
            <Typography variant="label">공유</Typography>
          </ButtonBase>
        </CardActions>
      </Box>
    </Card>
  );
};

export default PostCard;

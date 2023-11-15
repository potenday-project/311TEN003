"use client";

import { PostInterface } from "@/types/post/PostInterface";
import { MoreVertOutlined } from "@mui/icons-material";
import {
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
import useLikePostMutation from "@/queries/post/useLikePostMutation";
import useUnLikePostMutation from "@/queries/post/useUnLikePostMutation";
import "../newpost/quill.mention.css";
import { sanitize } from "isomorphic-dompurify";
import UserAvatar from "../user/info/UserAvatar";

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
  likedByMe,
  quoteCount,
}: PostInterface) => {
  const openPostDetailPage = useOpenPostDetailPage();
  const hasImage = useMemo(() => postAttachUrls.length !== 0, [postAttachUrls]);
  const { mutate: likeHandler } = useLikePostMutation();
  const { mutate: unLikeHandler } = useUnLikePostMutation();

  return (
    <Card sx={{ display: "flex", gap: 2, p: 2 }}>
      <UserAvatar
        src={profileImgUrls[0]}
        fallback={String(id)[0].toUpperCase()}
      />
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
        {alcoholName && (
          <AlcoleNameTag alcoholName={alcoholName} alcoholType={alcoholType} />
        )}

        <CardContent sx={{ px: 0 }}>
          {/* Contents */}
          <div
            dangerouslySetInnerHTML={{
              __html: sanitize(postContent),
            }}
          ></div>
          {/* Hash tags */}
          <PostHashTagList tags={tagList} />
        </CardContent>

        {/* image */}
        {hasImage && (
          <CardMedia
            data-testid="postImg"
            component="img"
            height="142"
            onClick={() => openPostDetailPage(id, String(postNo))}
            image={postAttachUrls[0].attachUrl}
            alt={`${id}의 포스트`}
            sx={{ borderRadius: 2, bgcolor: "background.default" }}
          />
        )}
        {/* CTA */}
        <CardActions sx={{ px: 0, justifyContent: "end", gap: 2 }}>
          <ButtonBase
            data-testid="commentBtn"
            aria-label="comment"
            onClick={() => openPostDetailPage(id, String(postNo))}
          >
            <CommentIcon />
            <Typography variant="label">{commentCount ?? 0}</Typography>
          </ButtonBase>
          <ButtonBase
            data-testid="likeBtn"
            aria-label="like"
            onClick={() => {
              likedByMe ? unLikeHandler(postNo) : likeHandler(postNo);
            }}
          >
            <Box sx={{ color: likedByMe ? "primary.main" : "#d9d9d9" }}>
              <LikeIcon />
            </Box>
            <Typography variant="label">{likeCount ?? 0}</Typography>
          </ButtonBase>
          <ButtonBase data-testid="QuoteBtn" aria-label="Quote">
            <QuoteIcon />
            <Typography variant="label">{quoteCount ?? 0}</Typography>
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

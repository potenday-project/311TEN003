"use client";
import { POST_DETAIL } from "@/const/clientPath";
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
import Link from "next/link";

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
  return (
    <Link href={`${POST_DETAIL(userId, id)}`}>
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
              <Typography sx={{ fontWeight: "bold" }}>{nickname}</Typography>
              <Typography>{`@${userId}`}</Typography>
              <Typography>{createdAt}</Typography>
            </Box>

            <ButtonBase aria-label="settings" sx={{ p: 0 }}>
              <MoreVertOutlined />
            </ButtonBase>
          </Box>

          {/* Contents */}
          <CardContent sx={{ px: 0 }}>
            <Typography variant="body1">{content}</Typography>
            {/* Hash tags */}
            {tags?.length > 0 && (
              <Box
                data-testid="tags"
                sx={{
                  pt: 2,
                  display: "flex",
                  flexDirection: "row",
                  gap: "8px",
                }}
              >
                {tags.map((tag, i) => (
                  <Typography
                    component={"span"}
                    variant={"label"}
                    color="text.secondary"
                    key={i}
                  >
                    {`#${tag}`}
                  </Typography>
                ))}
              </Box>
            )}
          </CardContent>
          {/* image */}
          {image.length !== 0 && (
            <CardMedia
              data-testid="postImg"
              component="img"
              height="142"
              image={image[0]}
              alt={`${userId}의 포스트`}
              sx={{ borderRadius: 2, bgcolor: "background.default" }}
            />
          )}
          {/* CTA */}
          <CardActions sx={{ px: 0, justifyContent: "end", gap: 2 }}>
            <ButtonBase data-testid="commentBtn" aria-label="share">
              <Typography>댓글</Typography>
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
    </Link>
  );
};

export default PostCard;

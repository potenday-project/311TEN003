"use client";

import { PostInterface } from "@/types/post/PostInterface";

import {
  Box,
  Card,
  CardActions,
  CardContent,
  CardMedia,
  Typography,
  ButtonBase,
  Stack,
} from "@mui/material";
import PostHashTagList from "./PostHashtagList";
import { useOpenPostDetailPage } from "@/hooks/useOpenPostDetailPage";
import { useContext, useMemo } from "react";
import ShareIcon from "@/assets/icons/ShareIcon.svg";
import LikeIcon from "@/assets/icons/LikeIcon.svg";
import CommentIcon from "@/assets/icons/CommentIcon.svg";
import AlcoholNameTag from "@/components/wiki/AlcoholNameTag";
import dayjs from "dayjs";
import useLikePostMutation from "@/queries/post/useLikePostMutation";
import useUnLikePostMutation from "@/queries/post/useUnLikePostMutation";
import "../newpost/quill.mention.css";
import { sanitize } from "isomorphic-dompurify";
import UserAvatar from "../user/info/UserAvatar";
import Link from "next/link";
import HOME, { USER_PAGE } from "@/const/clientPath";
import { useMyInfoQuery } from "@/queries/auth/useMyInfoQuery";
import PostCardOptionDropdown from "./PostCardOptionDropdown";
import { postcardContext } from "@/store/post/PostCardContext";
import { useDeletePostMutation } from "@/queries/post/useDeletePostMutation";
import useDeleteAttachMutation from "@/queries/attach/useDeleteAttachMutation";
import { useRouter } from "next/navigation";

const PostCard = ({
  postAttachUrls,
  id,
  createdBy,
  nickname,
  postContent,
  lastModifiedDate,
  tagList,
  postNo,
  likeCount,
  profileImgUrls,
  alcoholName,
  alcoholType,
  commentCount,
  likedByMe,
  alcoholNo,
}: PostInterface) => {
  const openPostDetailPage = useOpenPostDetailPage();
  const router = useRouter();
  
  const hasImage = useMemo(() => postAttachUrls.length !== 0, [postAttachUrls]);

  const searchContext = useContext(postcardContext);

  const { mutate: likeHandler } = useLikePostMutation(searchContext);
  const { mutate: unLikeHandler } = useUnLikePostMutation(searchContext);

  const { mutateAsync: deletePost } = useDeletePostMutation();
  const { mutateAsync: deleteFile } = useDeleteAttachMutation();

  const deleteHandler = async () => {
    if (confirm("정말 삭제하시겠습니까?")) {
      await deletePost(postNo);
      postAttachUrls?.[0].attachUrl &&
        (await deleteFile(postAttachUrls?.[0].attachUrl));
      router.push(HOME);
    }
  };
  
  const { data: currentUser } = useMyInfoQuery();

  const isMyPost = useMemo(
    () => currentUser?.userNo === createdBy,
    [currentUser]
  );

  return (
    <Card sx={{ display: "flex", gap: 2, p: 2 }}>
      <Link href={USER_PAGE(createdBy)}>
        <UserAvatar
          src={profileImgUrls[0]}
          fallback={String(id)[0].toUpperCase()}
        />
      </Link>
      <Box sx={{ width: "100%" }}>
        {/* Header */}
        <Stack
          data-testid="mui-header"
          direction="row"
          justifyContent="space-between"
          px={0}
        >
          <Stack
            direction="row"
            gap={1}
            sx={{
              height: 24,
              alignItems: "center",
            }}
          >
            {/* 타이틀 */}
            <Link href={USER_PAGE(createdBy)}>
              <Typography color="primary.main">{nickname}</Typography>
            </Link>
            <Link href={USER_PAGE(createdBy)}>
              <Typography
                variant="label"
                color={"text.secondary"}
              >{`@${id}`}</Typography>
            </Link>
            <Typography variant="label" color={"text.secondary"}>
              {dayjs(lastModifiedDate).format("MM.DD")}
            </Typography>
          </Stack>

          {isMyPost && (
            <PostCardOptionDropdown
              postId={postNo}
              filePk={postAttachUrls?.[0]?.attachNo}
            />
          )}
        </Stack>

        {alcoholName && (
          <AlcoholNameTag
            alcoholNo={alcoholNo}
            alcoholName={alcoholName}
            alcoholType={alcoholType}
          />
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
            sx={{
              borderRadius: 2,
              bgcolor: "background.default",
              cursor: "pointer",
            }}
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

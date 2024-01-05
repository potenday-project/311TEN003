import PostHashTagList from "@/components/post/PostHashtagList";
import { USER_PAGE } from "@/const/clientPath";
import { PostInterface } from "@/types/post/PostInterface";
import { Stack, Typography, Box } from "@mui/material";
import { sanitize } from "isomorphic-dompurify";
import Link from "next/link";
import React from "react";
import UserAvatar from "./../../user/info/UserAvatar";
import Image from "next/image";
import { useOpenPostDetailPage } from "@/hooks/useOpenPostDetailPage";

interface AlcoholeDetailPostCardProps extends PostInterface {}

const AlcoholeDetailPostCard = ({
  nickname,
  id,
  createdBy,
  tagList,
  postAttachUrls,
  profileImgUrls,
  postNo,
  postContent,
}: AlcoholeDetailPostCardProps) => {
  const hasImage = postAttachUrls?.length > 0;
  const openPostDetailPage = useOpenPostDetailPage();
  return (
    <Stack direction={"row"} gap={1}>
      <UserAvatar
        sx={{ width: 24, height: 24 }}
        fallback={id?.[0]?.toUpperCase()}
        src={profileImgUrls[0]?.attachUrl}
      />
      <Stack flexGrow={1}>
        <Stack direction="row" gap={1}>
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
        </Stack>

        <Box
          onClick={() => openPostDetailPage(id, String(postNo))}
          className="line-clamp-3"
          flexGrow={1}
          dangerouslySetInnerHTML={{ __html: sanitize(postContent) }}
        />
        <PostHashTagList tags={tagList} color="primary.main" />
      </Stack>
      {hasImage && (
        <Image
          src={postAttachUrls?.[0]?.attachUrl}
          width={56}
          height={56}
          alt={`${nickname}님이 올린 이미지`}
          style={{ borderRadius: "8px", objectFit: "cover" }}
          onClick={() => openPostDetailPage(id, String(postNo))}
        />
      )}
    </Stack>
  );
};

export default AlcoholeDetailPostCard;

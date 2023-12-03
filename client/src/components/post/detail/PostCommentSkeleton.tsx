import { Skeleton, Stack, Card } from "@mui/material";

const PostCommentSkeleton = () => {
  return (
    <Card sx={{ display: "flex", gap: 2, p: 2 }}>
      <Stack direction="row" gap={1.25} flexGrow={1}>
        <Skeleton width={40} height={40} variant="circular" />
        <Stack gap={1} flexGrow={1}>
          <Stack direction="row" gap={1}>
            <Skeleton width={50} />
            <Skeleton width={50} />
            <Skeleton width={50} />
          </Stack>
          <Skeleton width={"100%"} />
          <Skeleton width={"70%"} />
        </Stack>
      </Stack>
    </Card>
  );
};

export default PostCommentSkeleton;

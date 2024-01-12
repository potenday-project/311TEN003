import { Box, Card, Skeleton, Stack } from "@mui/material";

const PostCardSkeleton = () => {
  return (
    <Card sx={{ display: "flex", gap: 2, p: 2 }}>
      <Skeleton width={40} height={40} variant="circular" />
      <Box sx={{ flexGrow: 1 }}>
        <Stack justifyContent="space-between" gap={2} px={0}>
          <Stack
            direction="row"
            gap={1}
            alignItems= {"center"}
          >
            <Skeleton width={100} />
            <Skeleton width={80} />
            <Skeleton width={50} />
          </Stack>
          <Stack>
            <Skeleton width={'100%'} />
            <Skeleton width={'50%'} />
          </Stack>
          <Skeleton width={"100%"} height={142} variant="rectangular" />
          <Stack direction="row" justifyContent={"end"} gap={2}>
            <Skeleton width={50} />
            <Skeleton width={50} />
            <Skeleton width={50} />
            <Skeleton width={50} />
          </Stack>
        </Stack>
      </Box>
    </Card>
  );
};

export default PostCardSkeleton;

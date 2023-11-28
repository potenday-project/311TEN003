import { Avatar, Box, Skeleton, Stack } from "@mui/material";

const UserInfoCardSkeleton = () => {
  return (
    <Stack alignItems="center" gap={1}>
      <Avatar sx={{ width: "56px", height: "56px" }} />
      <Stack direction="row" gap={1}>
        <Skeleton width={124} />
        <Skeleton width={124} />
      </Stack>
      <Box sx={{ height: 48 }}>
        <Skeleton width={124} />
      </Box>
      <Stack direction="row" gap={1}>
        <Skeleton width={60} />
        <Skeleton width={60} />
      </Stack>
      <Skeleton variant='rounded' width="100%" height={40} />
    </Stack>
  );
};

export default UserInfoCardSkeleton;

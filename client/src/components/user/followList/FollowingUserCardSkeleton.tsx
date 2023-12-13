import { Stack } from "@mui/material";
import { Skeleton } from "@mui/material";

const FollowingUserCardSkeleton = () => {
  return (
    <Stack direction="row" gap={1} py={1}>
      <Skeleton width={40} height={40} variant="circular" />
      <Stack gap={1} flexGrow={1}>
        <Stack direction="row" justifyContent={"space-between"}>
          <Stack>
            <Skeleton width={50} />
            <Skeleton width={60} />
          </Stack>
          <Skeleton height={40} width={80} variant="rectangular" />
        </Stack>
        <Skeleton width={"100%"} />
      </Stack>
    </Stack>
  );
};

export default FollowingUserCardSkeleton;

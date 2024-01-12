import { Box, Card, CardProps, Skeleton, Stack } from "@mui/material";
import { Ref, forwardRef } from "react";

const PostCardSkeleton = (
  cardProps?: Omit<CardProps, "elevation">,
  ref?: Ref<HTMLDivElement>
) => {
  return (
    <Card
      sx={{ display: "flex", gap: 2, py: 2 }}
      ref={ref}
      elevation={0}
      {...cardProps}
    >
      <Skeleton width={40} height={40} variant="circular" />
      <Box sx={{ flexGrow: 1 }}>
        <Stack justifyContent="space-between" gap={2} px={0}>
          <Stack direction="row" gap={1} alignItems={"center"}>
            <Skeleton width={100} />
            <Skeleton width={80} />
            <Skeleton width={50} />
          </Stack>
          <Stack>
            <Skeleton width={"100%"} />
            <Skeleton width={"50%"} />
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

export default forwardRef(PostCardSkeleton);

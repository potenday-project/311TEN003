import { memo } from "react";
import { Skeleton } from "@mui/material";

import useSkeletonTimer from "@/hooks/useSkeletonTimer";

interface AlcoholListSkeletonInterface {
  size?: number;
  disableTimer?: boolean;
}

const AlcoholListSkeleton = memo(
  ({ size = 5, disableTimer }: AlcoholListSkeletonInterface) => {
    const isOver200ms = !!disableTimer ? true : useSkeletonTimer();

    return isOver200ms ? (
      Array.from(new Array(size)).map((_e, i) => (
        <Skeleton
          key={i}
          variant="rectangular"
          width={"100%"}
          height={40}
          sx={{ borderRadius: 2 }}
        />
      ))
    ) : (
      <></>
    );
  }
);

export default AlcoholListSkeleton;

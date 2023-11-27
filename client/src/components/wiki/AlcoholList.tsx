"use client";
import AlcoholNameTag from "@/components/wiki/AlcoholNameTag";
import useGetAlcoholListQuery from "@/queries/alcohol/useGetAlcoholListQuery";
import { Box, Pagination, Skeleton, Stack } from "@mui/material";
import { memo } from "react";

const AlcoholList = () => {
  const { data: alcohols } = useGetAlcoholListQuery();
  return (
    <Stack alignItems="center" gap={2}>
      <Stack gap={1} alignItems="center" width={"100%"} height={"232px"}>
        {alcohols ? (
          alcohols.list.map((alcohol) => (
            <AlcoholNameTag
              key={alcohol.alcoholNo}
              alcoholName={alcohol.alcoholName}
              alcoholType={alcohol.alcoholType}
            />
          ))
        ) : (
          <AlcoholListSkeleton />
        )}
      </Stack>
      <Pagination count={alcohols?.totalCount} />
    </Stack>
  );
};

const AlcoholListSkeleton = memo(() => {
  return Array.from(new Array(5)).map(() => (
    <Skeleton
      variant="rectangular"
      width={"100%"}
      height={40}
      sx={{ borderRadius: 2 }}
    />
  ));
});

export default AlcoholList;

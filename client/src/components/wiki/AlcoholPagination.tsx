"use client";
import useGetAlcoholListQuery from "@/queries/alcohol/useGetAlcoholListQuery";
import AlcoholList from "@/components/wiki/AlcoholList";
import { Pagination, Stack } from "@mui/material";
import AlcoholListSkeleton from "@/components/wiki/AlcoholListSkeleton";

const AlcoholPagenation = () => {
  const { data: alcohols, isSuccess } = useGetAlcoholListQuery();

  return (
    <Stack alignItems="center" gap={2}>
      <Stack gap={1} alignItems="center" width={"100%"} height={"232px"}>
        {isSuccess ? (
          <AlcoholList data={alcohols?.list} />
        ) : (
          <AlcoholListSkeleton disableTimer />
        )}
      </Stack>
      <Pagination count={alcohols?.totalCount} />
    </Stack>
  );
};

export default AlcoholPagenation;

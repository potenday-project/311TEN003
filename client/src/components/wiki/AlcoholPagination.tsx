"use client";
import useGetAlcoholListQuery from "@/queries/alcohol/useGetAlcoholListQuery";
import AlcoholList from "@/components/wiki/AlcoholList";
import { Pagination, Stack } from "@mui/material";
import usePushToWikiDetail from "@/hooks/wiki/usePushToWikiDetail";

const AlcoholPagenation = () => {
  const { data: alcohols } = useGetAlcoholListQuery();
  const onClickElementHandler = usePushToWikiDetail();
  return (
    <Stack alignItems="center" gap={2}>
      <Stack gap={1} alignItems="center" width={"100%"} height={"232px"}>
        <AlcoholList
          data={alcohols?.list}
          onClickElement={onClickElementHandler}
        />
      </Stack>
      <Pagination count={alcohols?.totalCount} />
    </Stack>
  );
};

export default AlcoholPagenation;

"use client";
import AlcoholNameTag from "@/components/wiki/AlcoholNameTag";
import useGetAlcoholListQuery from "@/queries/alcohol/useGetAlcoholListQuery";
import { Pagination, Stack } from "@mui/material";

const AlcoholList = () => {
  const { data: alcohols } = useGetAlcoholListQuery();
  return (
    <Stack alignItems="center" gap={2}>
      <Stack gap={1} alignItems="center" width={'100%'}>
        {alcohols &&
          alcohols.list.map((alcohol) => (
            <AlcoholNameTag
              key={alcohol.alcoholNo}
              alcoholName={alcohol.alcoholName}
              alcoholType={alcohol.alcoholType}
            />
          ))}
      </Stack>
      <Pagination count={alcohols?.totalCount} />
    </Stack>
  );
};

export default AlcoholList;

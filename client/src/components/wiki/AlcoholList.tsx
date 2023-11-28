"use client";
import AlcoholNameTag from "@/components/wiki/AlcoholNameTag";
import { AlcoholDetailInterface } from "@/types/alcohol/AlcoholInterface";
import { Typography } from "@mui/material";
import { memo } from "react";

const AlcoholList = ({
  data: alcohols,
}: {
  data: AlcoholDetailInterface[];
}) => {
  return (
    <>
      {alcohols?.length > 0 ? (
        alcohols.map((alcohol) => (
          <AlcoholNameTag
            key={alcohol.alcoholNo}
            alcoholName={alcohol.alcoholName}
            alcoholType={alcohol.alcoholType}
          />
        ))
      ) : (
        <Typography textAlign="center">검색 결과가 없어요</Typography>
      )}
    </>
  );
};
export default memo(AlcoholList);


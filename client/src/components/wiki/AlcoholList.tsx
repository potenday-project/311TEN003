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
        alcohols.map(({ alcoholName, alcoholNo, alcoholType }) => (
          <AlcoholNameTag
            key={alcoholNo}
            alcoholName={alcoholName}
            alcoholType={alcoholType}
            alcoholNo={alcoholNo}
          />
        ))
      ) : (
        <Typography textAlign="center">검색 결과가 없어요</Typography>
      )}
    </>
  );
};
export default memo(AlcoholList);

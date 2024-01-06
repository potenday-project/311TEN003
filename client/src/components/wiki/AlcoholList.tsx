"use client";
import AlcoholNameTag from "@/components/wiki/AlcoholNameTag";
import { AlcoholDetailInterface } from "@/types/alcohol/AlcoholInterface";
import { Typography } from "@mui/material";
import { memo } from "react";
import AlcoholListSkeleton from "./AlcoholListSkeleton";

interface AlcoholListInterface {
  data?: AlcoholDetailInterface[];
  onClickElement?: (data: AlcoholDetailInterface) => void;
  size?:number
}
/**
 * 술 정보 Array 를 입력받아 List로 맵핑해주는 컴포넌트
 * onClickElement 속성으로 각 엘리먼트 클릭 시 속성을 지정가능
 * @returns
 */
const AlcoholList = ({ data: alcohols, onClickElement,size=5 }: AlcoholListInterface) => {
  return alcohols ? (
    <>
      {alcohols.length > 0 ? (
        alcohols.map((alcohol) => {
          const { alcoholName, alcoholNo, alcoholType } = alcohol;
          return (
            <AlcoholNameTag
              key={alcoholNo}
              alcoholName={alcoholName}
              alcoholType={alcoholType}
              alcoholNo={alcoholNo}
              onClick={() => {
                onClickElement && onClickElement(alcohol);
              }}
            />
          );
        })
      ) : (
        <Typography textAlign="center">검색 결과가 없어요</Typography>
      )}
    </>
  ) : (
    <AlcoholListSkeleton size={size} disableTimer/>
  );
};
export default memo(AlcoholList);

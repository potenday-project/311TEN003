"use client";
import { useState } from "react";
import AlcoholPagination from "@/components/wiki/AlcoholPagination";
import WikiAlcoholSelector from "@/components/wiki/WikiAlcoholSelector";
import SectionHeading from "@/components/SectionHeading";
import { Stack } from "@mui/material";

const WikiPage = () => {
  const [currentAlcoholNo, setCurrentAlcoholNo] = useState<number | undefined>(
    undefined
  );

  return (
    <Stack gap={2}>
      <SectionHeading
        title={"술 정보"}
        subTitle={"다양한 술 정보를 알아보세요!"}
      />
      <WikiAlcoholSelector
        onChange={(alcoholNo) => {
          setCurrentAlcoholNo(alcoholNo);
        }}
      />
      <AlcoholPagination alcoholTypeNo={currentAlcoholNo} />
    </Stack>
  );
};

export default WikiPage;

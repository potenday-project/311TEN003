"use client";
import { useState, useCallback, useMemo, useEffect } from "react";
import { Stack, StackProps } from "@mui/material";
import WikiAlcoholSelectorBtn from "./WikiAlcoholSelectorBtn";
import WineIcon from "@/assets/icons/Alcohol/WineIcon.svg";
import BrandyIcon from "@/assets/icons/Alcohol/BrandyIcon.svg";
import AllAlcoholIcon from "@/assets/icons/Alcohol/AllAlcoholIcon.svg";
import WiskyIcon from "@/assets/icons/Alcohol/WiskyIcon.svg";
import LiquorIcon from "@/assets/icons/Alcohol/LiquorIcon.svg";
import BeerIcon from "@/assets/icons/Alcohol/BeerIcon.svg";
import RumIcon from "@/assets/icons/Alcohol/RumIcon.svg";

import TraditionalAlcoholIcon from "@/assets/icons/Alcohol/TraditionalAlcoholIcon.svg";
import SakeIcon from "@/assets/icons/Alcohol/SakeIcon.svg";

interface WikiAlcoholSelectorInterface extends Omit<StackProps, "onChange"> {
  onChange?: (selectedAlcoholNo: number | undefined) => void;
}

const WikiAlcoholSelector = ({ onChange }: WikiAlcoholSelectorInterface) => {
  const btnList = useMemo(
    () => [
      {
        title: "전체",
        iconComponent: <AllAlcoholIcon />,
        alcoholTypeNo: undefined,
      },
      { title: "포도주", iconComponent: <WineIcon />, alcoholTypeNo: 167 },
      { title: "브랜디", iconComponent: <BrandyIcon />, alcoholTypeNo: 69 },
      { title: "위스키", iconComponent: <WiskyIcon />, alcoholTypeNo: 111 },
      { title: "리큐르", iconComponent: <LiquorIcon />, alcoholTypeNo: 33 },
      { title: "맥주", iconComponent: <BeerIcon />, alcoholTypeNo: 2 },

      {
        title: "우리술",
        iconComponent: <TraditionalAlcoholIcon />,
        alcoholTypeNo: 109,
      },
      { title: "사케", iconComponent: <SakeIcon />, alcoholTypeNo: 78 },
      { title: "럼", iconComponent: <RumIcon />, alcoholTypeNo: 28 },
      {
        title: "미분류",
        iconComponent: <AllAlcoholIcon />,
        alcoholTypeNo: 149,
      },
    ],
    []
  );

  const [selectedAlcohol, setSelectedAlcohol] = useState(
    btnList[0].alcoholTypeNo
  );

  useEffect(() => {
    onChange?.(selectedAlcohol);
  }, [selectedAlcohol]);

  const clickHandler = useCallback((alcoholTypeNo: number | undefined) => {
    setSelectedAlcohol(alcoholTypeNo);
  }, []);

  return (
    <Stack width="100%" sx={{ overflowX: "auto" }}>
      <Stack direction="row" justifyContent="center" gap={2} mx="auto">
        {btnList.map(({ alcoholTypeNo, title, iconComponent }) => (
          <WikiAlcoholSelectorBtn
            key={title}
            title={title}
            isSelected={selectedAlcohol === alcoholTypeNo}
            onClick={() => clickHandler(alcoholTypeNo)}
            iconComponent={iconComponent}
          />
        ))}
      </Stack>
    </Stack>
  );
};

export default WikiAlcoholSelector;

"use client";
import { useState, useCallback, useMemo } from "react";
import { Stack } from "@mui/material";
import WikiAlcoholSelectorBtn from "./WikiAlcoholSelectorBtn";
import WineIcon from "@/assets/icons/Alcohol/WineIcon.svg";
import WiskyIcon from "@/assets/icons/Alcohol/WiskyIcon.svg";
import SpiritsIcon from "@/assets/icons/Alcohol/SpiritsIcon.svg";
import TraditionalAlcoholIcon from "@/assets/icons/Alcohol/TraditionalAlcoholIcon.svg";
import SakeIcon from "@/assets/icons/Alcohol/SakeIcon.svg";

const WikiAlcoholSelector = () => {
  
  const btnList =useMemo(()=>[
    { title: "포도주", iconComponent: <WineIcon /> },
    { title: "위스키", iconComponent: <WiskyIcon /> },
    { title: "증류주", iconComponent: <SpiritsIcon /> },
    { title: "우리술", iconComponent: <TraditionalAlcoholIcon /> },
    { title: "사케", iconComponent: <SakeIcon /> },
  ],[]) 

  const [selectedAlcohol, setSelectedAlcohol] = useState(btnList[0].title);

  const clickHandler = useCallback((title:string)=>{
    setSelectedAlcohol(title)
  },[])

  return (
    <Stack direction="row" justifyContent='center' gap={2}>
      {btnList.map((btnInfo) => (
        <WikiAlcoholSelectorBtn
          key={btnInfo.title}
          isSelected={selectedAlcohol === btnInfo.title}
          onClick={()=>clickHandler(btnInfo.title)}
          {...btnInfo}
        />
      ))}
    </Stack>
  );
};

export default WikiAlcoholSelector;

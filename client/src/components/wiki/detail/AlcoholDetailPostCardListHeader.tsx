"use client";

import React, { ReactNode } from "react";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import LogoLarge from "@/assets/icons/LogoLarge.svg";
import { Stack, Typography, ButtonBase, useTheme } from "@mui/material";
import { useRouter } from "next/navigation";

interface AlcoholDetailPostCardListHeaderProps {
  totalContents: number;
  href: string;
}

const AlcoholDetailPostCardListHeader = ({
  totalContents,
  href,
}: AlcoholDetailPostCardListHeaderProps) => {
  const primaryColor = useTheme()?.palette?.primary?.main ?? "#7B1FA2";

  const router = useRouter();
  const routerHandler = () => {
    router.push(href);
  };

  return (
    <AlcoholDetailPostCardListHeaderWrapper>
      <Stack direction="row" gap={1} alignItems="center" flexGrow={1}>
        <LogoLarge width={24} color={primaryColor} />
        <Typography>캐스크</Typography>
        <Typography variant="subtitle2" fontWeight="bold" color="primary.main">
          {totalContents}
        </Typography>
      </Stack>
      <Stack
        component={ButtonBase}
        onClick={routerHandler}
        direction="row"
        gap={1}
        color="primary.main"
      >
        <Typography>캐스크 모아보기</Typography>
        <ArrowForwardIosIcon sx={{ color: "primary.main" }} />
      </Stack>
    </AlcoholDetailPostCardListHeaderWrapper>
  );
};

const AlcoholDetailPostCardListHeaderWrapper = ({
  children,
}: {
  children: ReactNode;
}) => (
  <Stack
    position={"absolute"}
    left={0}
    right={0}
    direction="row"
    justifyContent="space-between"
    alignItems="center"
    bgcolor={"background.paper"}
    px={2}
    py={1.5}
    sx={{ borderBottom: "1px solid", borderColor: "gray.primary" }}
  >
    {children}
  </Stack>
);

export default AlcoholDetailPostCardListHeader;

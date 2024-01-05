"use client";
import useGetAlcoholDetailQuery from "@/queries/alcohol/useGetAlcoholDetailQuery";
import { AlcoholDetailInterface } from "@/types/alcohol/AlcoholInterface";
import { Stack, Typography, Divider } from "@mui/material";
import { ReactNode } from "react";
import AlcoholDetailThumbnail from "./AlcoholDetailThumbnail";

type Props = {
  alcoholNo: string;
  initialData: AlcoholDetailInterface;
  children?: ReactNode;
};

const AlcoholDetailPage = ({ alcoholNo, initialData, children }: Props) => {
  const { data: alcoholInfo } = useGetAlcoholDetailQuery(
    alcoholNo,
    initialData
  );

  return (
    <Stack gap={2}>
      <AlcoholDetailThumbnail
        title={alcoholInfo.alcoholName}
        type={alcoholInfo.alcoholType}
        src={alcoholInfo.alcoholAttachUrls[0]?.attachUrl}
      />
      {children}

      <Stack gap={2} pt={2}>
        <Typography
          variant={"subtitle1"}
          color="primary.main"
          fontWeight="bold"
        >
          Tasting Notes
        </Typography>
        <Stack gap={2}>
          <Stack direction="row">
            <Typography width="78px">Aroma</Typography>
            <Typography fontWeight="bold">
              {alcoholInfo.taste.Aroma.join(", ")}
            </Typography>
          </Stack>
          <Stack direction="row">
            <Typography width="78px">Taste</Typography>
            <Typography fontWeight="bold">
              {alcoholInfo.taste.Taste.join(", ")}
            </Typography>
          </Stack>
          <Stack direction="row">
            <Typography width="78px">Finish</Typography>
            <Typography fontWeight="bold">
              {alcoholInfo.taste.Finish.join(", ")}
            </Typography>
          </Stack>
        </Stack>
      </Stack>

      <Divider sx={{ borderColor: "#F6EAFB" }} />

      <Stack gap={2}>
        <Typography
          variant={"subtitle1"}
          color="primary.main"
          fontWeight="bold"
        >
          Information
        </Typography>
        <Stack gap={2}>
          <Stack direction="row">
            <Typography width="78px">종류</Typography>
            <Typography fontWeight="bold">{alcoholInfo.alcoholType}</Typography>
          </Stack>
          <Stack direction="row">
            <Typography width="78px">도수</Typography>
            <Typography fontWeight="bold">{alcoholInfo.degree}%</Typography>
          </Stack>
          <Stack direction="row">
            <Typography width="78px">용량</Typography>
            <Typography fontWeight="bold">{alcoholInfo.volume}ml</Typography>
          </Stack>
        </Stack>
      </Stack>
    </Stack>
  );
};

export default AlcoholDetailPage;

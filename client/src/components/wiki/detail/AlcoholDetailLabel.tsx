import { Stack, Typography } from "@mui/material";
import React from "react";

interface AlcoholDetailLabelProps {
  title: string;
  type: string;
}

const AlcoholDetailLabel = ({ title, type }: AlcoholDetailLabelProps) => {
  return (
    <Stack
      bgcolor={"rgba(123, 31, 162, 0.4)"}
      gap={1}
      sx={{
        borderRadius: 3,
        p: 2,
        backdropFilter: "blur(10px)",
        color: "background.paper",
      }}
    >
      <Typography variant='label'>{type}</Typography>
      <Typography variant='subtitle2' fontWeight="bold">{title}</Typography>
    </Stack>
  );
};

export default AlcoholDetailLabel;

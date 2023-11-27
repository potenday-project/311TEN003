import { Stack, StackProps, Typography } from "@mui/material";
import React from "react";

interface SectionHeadingProps extends StackProps {
  title?: string;
  subTitle?: string;
}

const SectionHeading = ({
  title,
  subTitle,
  ...stackProps
}: SectionHeadingProps) => {
  return (
    <Stack {...stackProps}>
      <Typography variant={"subtitle2"} sx={{ fontWeight: "bold" }}>
        {title}
      </Typography>
      <Typography variant={"label"} sx={{ color: "text.secondary" }}>
        {subTitle}
      </Typography>
    </Stack>
  );
};

export default SectionHeading;

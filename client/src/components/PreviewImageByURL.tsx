import { Box, BoxProps } from "@mui/material";
import React, { Ref, forwardRef } from "react";

interface PreviewImageByURLProps extends BoxProps {
  fileUrl: string | ArrayBuffer;
}

const PreviewImageByURL = (
  { fileUrl, sx }: PreviewImageByURLProps,
  ref: Ref<"div">
) => {
  return (
    <Box
      sx={{
        backgroundImage: `url(${fileUrl})`,
        width: "100%",
        height: 142,
        borderRadius: 4,
        border: "1px solid",
        borderColor: "gray.secondary",
        backgroundRepeat: "no-repeat",
        backgroundSize: "cover",
        backgroundPosition: "center",
        ...sx,
      }}
      ref={ref}
    />
  );
};

export default forwardRef(PreviewImageByURL);

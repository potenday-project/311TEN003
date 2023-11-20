"use client";

import {
  SnackbarVariant,
  useGlobalSnackbarStore,
} from "@/store/useGlobalSnackbarStore";
import { CheckCircle, Error, Warning } from "@mui/icons-material";
import { Snackbar, SnackbarContent, Stack } from "@mui/material";

const GlobalToast = () => {
  const { isOpen, variant, message, closeToast } = useGlobalSnackbarStore();
  return (
    <Snackbar
      open={isOpen}
      color={variant}
      key={message}
      onClose={closeToast}
      autoHideDuration={3000}
      anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      sx={{ mb: 6 }}
    >
      <SnackbarContent
        message={<SnackbarMessage variant={variant} message={message} />}
      />
    </Snackbar>
  );
};

const SnackbarMessage = ({
  message,
  variant,
}: {
  message: string;
  variant: SnackbarVariant;
}) => {
  return (
    <Stack direction="row" gap={1} alignItems="center">
      {IconSelector(variant)}
      {message}
    </Stack>
  );
};

const IconSelector = (variant: SnackbarVariant) => {
  switch (variant) {
    case "danger":
      return <Error sx={{color:'red'}} />;
    case "warning":
      return <Warning sx={{color:'orange'}}/>;
    default:
      return <CheckCircle sx={{color:'green'}}/>;
  }
};

export default GlobalToast;

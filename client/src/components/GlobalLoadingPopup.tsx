"use client";
import { useGlobalLoadingStore } from "@/store/useGlobalLoadingStore";
import { CircularProgress, Modal } from "@mui/material";

const GlobalLoadingPopup = () => {
  const { isLoading } = useGlobalLoadingStore();
  return (
    <Modal
      open={isLoading}
      disablePortal
      disableAutoFocus
      sx={{
        alignItems: "center",
        justifyContent: "center",
        zIndex: 9999,
        display: "flex",
      }}
    >
      <CircularProgress />
    </Modal>
  );
};

export default GlobalLoadingPopup;

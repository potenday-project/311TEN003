import {
  Box,
  SwipeableDrawer,
  SwipeableDrawerProps,
  styled,
} from "@mui/material";
import React from "react";

interface Props extends SwipeableDrawerProps {}

const CustomSwipeableDrawer = ({ open, onOpen, onClose, children }: Props) => {
  const pullerBleed = 24;

  return (
    <SwipeableDrawer
      open={open}
      onOpen={onOpen}
      onClose={onClose}
      anchor="bottom"
      disableSwipeToOpen
      PaperProps={{
        sx: {
          p: 2,
          borderTopLeftRadius: pullerBleed,
          borderTopRightRadius: pullerBleed,
          overFlow: "hidden",
        },
      }}
      ModalProps={{
        keepMounted: false,
      }}
    >
      <Puller />
      <Box sx={{ mt: `${pullerBleed + 8}px` }}>{children}</Box>
    </SwipeableDrawer>
  );
};

const Puller = styled(Box)(() => ({
  width: 56,
  height: 4,
  backgroundColor: "#F6EAFB",
  borderRadius: 3,
  position: "absolute",
  top: 8,
  left: "calc(50% - 28px)",
}));

export default CustomSwipeableDrawer;

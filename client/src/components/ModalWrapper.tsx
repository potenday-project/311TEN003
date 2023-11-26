"use client";
import { Box, Modal, ModalProps } from "@mui/material";
import { useRouter } from "next/navigation";

import { ReactNode } from "react";

interface ModalInterface
  extends Omit<ModalProps, "open" | "onClick" | "children"> {
  children?: ReactNode;
  /**
   * 기본으로 설정된 <Box/> 의 패딩, 배경색 속성을 제거
   */
  disableBox?: boolean;
}

/**
 * MUI <Modla/>과 <Box/>를 랩핑해놓은 글로벌 모달
 */
const ModalWrapper = ({ children, disableBox }: ModalInterface) => {
  const { back } = useRouter();

  return (
    <Modal
      open={true}
      onClick={() => back()}
      disablePortal
      disableAutoFocus
      sx={{
        alignItems: "center",
        justifyContent: "center",
        display: "flex",
      }}
    >
      {
        <Box
          onClick={(e) => e.stopPropagation()}
          sx={{
            bgcolor: disableBox ? undefined : "background.paper",
            p: disableBox ? 0 : 4,
            maxWidth: "90%",
            maxHeight: "90%",
            overflowY: "auto",
          }}
        >
          <>{children}</>
        </Box>
      }
    </Modal>
  );
};

export default ModalWrapper;

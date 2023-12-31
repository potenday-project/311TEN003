"use client";

import CustomAppbar from "@/components/layout/CustomAppbar";
import { appbarHeight } from "@/const/uiSizes";
import { Container, Stack } from "@mui/material";
import { ReactNode } from "react";

type Props = {
  children: ReactNode;
};

const UserInfoPageLayout = ({ children }: Props) => {
  return (
    <>
      <CustomAppbar title="설정" />
      <Container
        sx={{ px: { xs: 0, sm: 4 }, mt: appbarHeight }}
        maxWidth={"lg"}
      >
        <Stack gap={2}>{children}</Stack>
      </Container>
    </>
  );
};

export default UserInfoPageLayout;

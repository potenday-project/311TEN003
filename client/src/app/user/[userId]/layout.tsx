"use client";
import CustomAppbar from "@/components/CustomAppbar";
import { useMyInfoQuery } from "@/queries/auth/useMyInfoQuery";
import { Container, Paper } from "@mui/material";
import React, { useMemo } from "react";

type Props = {
  children: React.ReactNode;
  params: { userId: string };
};

const UserInfoPageLayout = ({ children, params }: Props) => {
  const { data: userInfo } = useMyInfoQuery();
  const isMyProfile = useMemo(
    () => String(userInfo?.userNo) === String(params.userId),
    [userInfo,params.userId]
  );

  return (
    <Paper>
      <CustomAppbar
        buttonTitle={isMyProfile ? "설정" : undefined}
        onClickButton={() => {
          if(!isMyProfile){
            return
          }
          console.log("눌림");
        }}
      />
      <Container sx={{ p: { xs: 0, sm: 4 } }} maxWidth={"lg"}>
        <Paper
          sx={{
            display: "flex",
            position: "relative",
            flexDirection: "column",
            gap: 2,
            p: 2,
          }}
        >
          {children}
        </Paper>
      </Container>
    </Paper>
  );
};

export default UserInfoPageLayout;

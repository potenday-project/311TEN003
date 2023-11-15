"use client";
import CustomAppbar from "@/components/CustomAppbar";
import { Container, Paper } from "@mui/material";
import React from "react";

type Props = {
  children: React.ReactNode;
};

const UserInfoPageLayout = ({ children }: Props) => {
  
  return (
    <Paper>
      <CustomAppbar
        title={""}
        buttonTitle={"설정"}
        onClickButton={() => {
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

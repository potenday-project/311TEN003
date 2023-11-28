"use client";
import CustomAppbar from "@/components/CustomAppbar";
import { SETTING_PAGE } from "@/const/clientPath";
import { useMyInfoQuery } from "@/queries/auth/useMyInfoQuery";
import UserPageContext from "@/store/user/UserPageContext";
import { Container, Paper } from "@mui/material";
import { useRouter } from "next/navigation";
import React, { useMemo, useState } from "react";

type Props = {
  children: React.ReactNode;
  params: { userId: string };
};

const UserInfoPageLayout = ({ children, params }: Props) => {
  const { data: userInfo } = useMyInfoQuery();
  const isMyProfile = useMemo(
    () => String(userInfo?.userNo) === String(params.userId),
    [userInfo, params.userId]
  );
  const [isEditing, setIsEditing] = useState(false);
  const router = useRouter()

  return (
    <UserPageContext.Provider value={{ isEditing, setIsEditing }}>
      <CustomAppbar
        buttonComponent={isMyProfile ? "설정" : undefined}
        onClickButton={() => {
          if (!isMyProfile) {
            return;
          }
          router.push(SETTING_PAGE)
        }}
      />
      <Container sx={{ px: { xs: 0, sm: 4 } }} maxWidth={"lg"}>
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
    </UserPageContext.Provider>
  );
};

export default UserInfoPageLayout;

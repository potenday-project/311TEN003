"use client";
import CustomAppbar from "@/components/layout/CustomAppbar";
import CustomContainer from "@/components/layout/CustomContainer";
import UserInfoEditingDrawer from "@/components/user/info/drawer/UserInfoEditingDrawer";
import { SETTING_PAGE } from "@/const/clientPath";
import { useMyInfoQuery } from "@/queries/auth/useMyInfoQuery";
import UserPageContext from "@/store/user/UserPageContext";
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
  const router = useRouter();

  return (
    <UserPageContext.Provider value={{ isEditing, setIsEditing }}>
      <CustomAppbar
        appendButton={isMyProfile ? "설정" : undefined}
        onClickAppend={() => {
          if (!isMyProfile) {
            return;
          }
          router.push(SETTING_PAGE);
        }}
      />
      <CustomContainer>
        <UserInfoEditingDrawer />
        {children}
      </CustomContainer>
    </UserPageContext.Provider>
  );
};

export default UserInfoPageLayout;

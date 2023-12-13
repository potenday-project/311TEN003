"use client";
import CustomAppbar from "@/components/layout/CustomAppbar";
import CustomContainer from "@/components/layout/CustomContainer";
import { useMyInfoQuery } from "@/queries/auth/useMyInfoQuery";
import { ReactNode } from "react";

type FollowListLayoutProps = {
  children: ReactNode;
};

const FollowListLayout = ({ children }: FollowListLayoutProps) => {
  const { data: myInfo } = useMyInfoQuery();
  
  return (
    <>
      <CustomAppbar title={`${myInfo?.nickname??'닉네임'}의 목록`}/>
      <CustomContainer>{children}</CustomContainer>
    </>
  );
};

export default FollowListLayout;

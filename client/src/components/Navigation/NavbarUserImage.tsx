"use client";
import { useMyInfoQuery } from "@/queries/auth/useMyInfoQuery";
import UserAvatar from "../user/info/UserAvatar";
import MyIcon from "~/assets/icons/MyIcon.svg";
const NavbarUserImage = () => {
  const { data } = useMyInfoQuery();
  return data ? (
    <UserAvatar
      sx={{ width: 28, height: 28, m: 0.25 }}
      src={data?.profileImages[0]?.attachUrl}
      fallback={data?.id}
    />
  ) : (
    <MyIcon />
  );
};

export default NavbarUserImage;

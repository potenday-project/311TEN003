"use client";
import { useUserInfoQuery } from "@/queries/auth/useUserInfoQuery";
import UserAvatar from "../user/info/UserAvatar";
import MyIcon from "~/assets/icons/MyIcon.svg";
const NavbarUserImage = () => {
  try {
    const { data } = useUserInfoQuery();
    return (
      <UserAvatar
        sx={{ width: 28, height: 28, m:0.25 }}
        src={data?.profileImages[0]?.attachUrl}
        fallback={data?.id}
      />
    );
  } catch (err) {
    return <MyIcon/>;
  }
};

export default NavbarUserImage;

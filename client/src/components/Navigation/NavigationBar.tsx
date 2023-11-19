"use client";
import { BottomNavigation, BottomNavigationAction, Paper } from "@mui/material";

import HomeIcon from "~/assets/icons/HomeIcon.svg";
import SearchIcon from "~/assets/icons/SearchIcon.svg";
import PostIcon from "~/assets/icons/PostIcon.svg";
import BeverageIcon from "~/assets/icons/BeverageIcon.svg";

import HOME, { MY_PROFILE, NEW_POST, SEARCH, SIGNIN, WIKI } from "@/const/clientPath";
import Link from "next/link";
import { usePathname } from "next/navigation";
import NavbarUserImage from "@/components/Navigation/NavbarUserImage";
import { useMemo } from "react";
import { useMyInfoQuery } from "@/queries/auth/useMyInfoQuery";

const NavigationBar = () => {
  const path = usePathname();
  const { data: userInfo } = useMyInfoQuery();
  const NavbarData = useMemo(
    () => [
      {
        iconComponent: <HomeIcon />,
        label: "홈",
        href: HOME,
      },
      {
        iconComponent: <SearchIcon />,
        label: "검색",
        href: SEARCH,
      },
      {
        iconComponent: <PostIcon />,
        href: NEW_POST,
      },
      {
        iconComponent: <BeverageIcon />,
        label: "술과사전",
        href: WIKI,
      },
      {
        iconComponent: <NavbarUserImage />,
        label: "내 정보",
        href: userInfo ? `${MY_PROFILE}/${userInfo.userNo}` : SIGNIN,
      },
    ],
    [userInfo]
  );
  return (
    <Paper sx={WrapperStyle} elevation={0}>
      <BottomNavigation value={path} showLabels sx={BtnStyle}>
        {NavbarData.map(({ label, href, iconComponent, ...others }) => {
          return (
            <BottomNavigationAction
              icon={iconComponent as any}
              key={String(label)}
              component={href ? Link : "button"}
              href={href}
              value={href}
              label={label}
              {...others}
            />
          );
        })}
      </BottomNavigation>
    </Paper>
  );
};

const WrapperStyle = {
  position: "fixed",
  bottom: 0,
  left: 0,
  right: 0,
  borderRadius: 0,
};
const BtnStyle = {
  borderRadius: "12px 12px 0 0",
  border: "1px solid",
  borderBottom: "none",
  borderColor: "gray.secondary",
  boxSizing: "border-box",
};

export default NavigationBar;

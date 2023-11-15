"use client";
import { BottomNavigation, BottomNavigationAction, Paper } from "@mui/material";

import HomeIcon from "~/assets/icons/HomeIcon.svg";
import SearchIcon from "~/assets/icons/SearchIcon.svg";
import PostIcon from "~/assets/icons/PostIcon.svg";
import BeverageIcon from "~/assets/icons/BeverageIcon.svg";
import MyIcon from "~/assets/icons/MyIcon.svg";
import HOME, { MY_PROFILE, NEW_POST, SEARCH, WIKI } from "@/const/clientPath";
import Link from "next/link";
import { usePathname } from "next/navigation";

const NavbarData = [
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
    iconComponent: <MyIcon />,
    label: "내 정보",
    //FIXME 실제 URL로 변경
    href: MY_PROFILE+"/1",
  },
];

const NavigationBar = () => {
  const path = usePathname();
  return (
    <Paper
      sx={{
        position: "fixed",
        bottom: 0,
        left: 0,
        right: 0,
        borderRadius: 0,
      }}
      elevation={0}
    >
      <BottomNavigation
        value={path}
        showLabels
        sx={{
          borderRadius: "12px 12px 0 0",
          border: "1px solid",
          borderBottom: "none",
          borderColor: "gray.secondary",
          boxSizing: "border-box",
        }}
      >
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

export default NavigationBar;

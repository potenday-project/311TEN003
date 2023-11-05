// FIXME "use client";
import { BottomNavigation, BottomNavigationAction, Paper } from "@mui/material";

import HomeIcon from "~/assets/icons/HomeIcon.svg";
import SearchIcon from "~/assets/icons/SearchIcon.svg";
import PostIcon from "~/assets/icons/PostIcon.svg";
import BeverageIcon from "~/assets/icons/BeverageIcon.svg";
import MyIcon from "~/assets/icons/MyIcon.svg";
import HOME, { MY_PROFILE, WIKI } from "@/const/clientPath";
import Link from "next/link";

const NavbarData = [
  {
    iconComponent: <img src={HomeIcon.src} />,
    label: "홈",
    href: HOME,
  },
  {
    iconComponent: <img src={SearchIcon.src} />,
    label: "검색",
  },
  {
    iconComponent: <img src={PostIcon.src} />,
    label: "올리기",
  },
  {
    iconComponent: <img src={BeverageIcon.src} />,
    label: "술과사전",
    href: WIKI,
  },
  {
    iconComponent: <img src={MyIcon.src} />,
    label: "내 정보",
    href: MY_PROFILE,
  },
];

const NavigationBar = () => {
  // FIXME const path = usePathname();
  return (
    <Paper
      sx={{ position: "fixed", bottom: 0, left: 0, right: 0 }}
      elevation={3}
    >
      <BottomNavigation
      // FIXME value={path}
      >
        {NavbarData.map((buttonData) => {
          return (
            <BottomNavigationAction
              icon={buttonData.iconComponent as any}
              key={buttonData.label}
              component={buttonData.href ? Link : "button"}
              href={buttonData.href}
              // FIXME value={buttonData.href}
              // FIXME label={buttonData.label}
            />
          );
        })}
      </BottomNavigation>
    </Paper>
  );
};

export default NavigationBar;

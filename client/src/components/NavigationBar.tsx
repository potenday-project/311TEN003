import {
  AppBar,
  ButtonBase,
  ButtonBaseProps,
  Toolbar,
  Typography,
} from "@mui/material";

import HomeIcon from "~/assets/icons/HomeIcon.svg";
import SearchIcon from "~/assets/icons/SearchIcon.svg";
import PostIcon from "~/assets/icons/PostIcon.svg";
import BeverageIcon from "~/assets/icons/BeverageIcon.svg";
import MyIcon from "~/assets/icons/MyIcon.svg";
import HOME, { MY_PROFILE, WIKI } from "@/const/clientPath";

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
  return (
    <AppBar position="fixed" sx={{ top: "auto", bottom: 0 }}>
      <Toolbar sx={{ mx: "auto", gap: 4 }}>
        {NavbarData.map((buttonData) => {
          return (
            <NavbarIconButton
              iconComponent={buttonData.iconComponent}
              key={buttonData.label}
              href={buttonData.href}
            >
              {buttonData.label}
            </NavbarIconButton>
          );
        })}
      </Toolbar>
    </AppBar>
  );
};

export default NavigationBar;

interface NavbarIconButtonInterface extends Omit<ButtonBaseProps, "children"> {
  children: string;
  iconComponent: React.ReactNode;
  href?: string;
}

export const NavbarIconButton = ({
  children,
  iconComponent,
  ...others
}: NavbarIconButtonInterface) => {
  return (
    <ButtonBase sx={{ flexDirection: "column", width: 56 }} {...others}>
      {iconComponent}
      <Typography variant="label" component="span">
        {children}
      </Typography>
    </ButtonBase>
  );
};

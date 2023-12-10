"use client";
import {
  AppBar,
  Button,
  IconButton,
  Toolbar,
  Typography,
  styled,
} from "@mui/material";
import GoBackIcon from "@/assets/icons/GoBackIcon.svg";
import { MouseEventHandler, ReactNode, memo } from "react";
import { useRouter } from "next/navigation";

interface CustomAppbarInterface {
  title?: string;
  prependButton?: ReactNode;
  onClickPrepend?: MouseEventHandler<HTMLButtonElement>;

  appendButton?: ReactNode;
  disableAppend?: boolean;
  onClickAppend?: MouseEventHandler<HTMLButtonElement>;
}

const CustomAppbar = ({
  title,
  appendButton,
  prependButton,
  onClickPrepend,
  disableAppend,
  onClickAppend,
}: CustomAppbarInterface) => {
  const router = useRouter();

  return (
    <AppBar position={"fixed"}>
      <Toolbar sx={{ display: "flex", justifyContent: "space-between" }}>
        {/* 프리팬드 버튼 */}
        {prependButton ? (
          <AppbarButton variant="text" onClick={onClickPrepend}>
            {prependButton}
          </AppbarButton>
        ) : (
          <IconButton
            onClick={(e) =>
              onClickPrepend ? onClickPrepend(e) : router.back()
            }
          >
            <GoBackIcon />
          </IconButton>
        )}
        {/* 타이틀 */}
        <Typography component="h1" variant="subtitle2" fontWeight={"bold"}>
          {title}
        </Typography>
        {/* 어팬드 버튼 */}
        {appendButton ? (
          <AppbarButton
            disabled={disableAppend}
            onClick={onClickAppend}
            variant="text"
          >
            {appendButton}
          </AppbarButton>
        ) : (
          <div style={{ width: "40px" }} />
        )}
      </Toolbar>
    </AppBar>
  );
};
const AppbarButton = styled(Button)(() => ({
  minWidth: 40,
  fontWeight: "medium",
  fontSize: "18px",
}));

export default memo(CustomAppbar);

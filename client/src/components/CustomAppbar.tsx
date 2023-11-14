import { AppBar, Button, IconButton, Toolbar, Typography } from "@mui/material";
import GoBackIcon from "@/assets/icons/GoBackIcon.svg";
import { MouseEventHandler, memo } from "react";
import { useRouter } from "next/navigation";

interface CustomAppbarInterface {
  title: string;
  buttonTitle: string;
  disableButton?: boolean;
  onClickButton?: MouseEventHandler<HTMLButtonElement>;
}

const CustomAppbar = ({
  title,
  buttonTitle,
  disableButton,
  onClickButton,
}: CustomAppbarInterface) => {
  const router = useRouter();

  return (
    <AppBar position={"static"}>
      <Toolbar sx={{ display: "flex", justifyContent: "space-between" }}>
        <IconButton onClick={() => router.back()}>
          <GoBackIcon></GoBackIcon>
        </IconButton>
        <Typography variant="subtitle2" fontWeight={"bold"}>
          {title}
        </Typography>
        <Button
          disabled={disableButton}
          onClick={onClickButton}
          variant="text"
          sx={{ minWidth: 40, fontWeight: "medium" }}
        >
          {buttonTitle}
        </Button>
      </Toolbar>
    </AppBar>
  );
};

export default memo(CustomAppbar);

import { createTheme } from "@mui/material";
import Pretendard from "~/assets/font/Pretendard";

const theme = createTheme({
  typography: {
    fontFamily: Pretendard.style.fontFamily,
  },
  palette: {
    mode: "light",
    primary: {
      main: "#82BCDB",
      contrastText: "#fff",
    },
    secondary: {
      main: "#00dde6",
    },
    text: {
      primary: "#3D4A59",
      secondary: "#558C99",
      disabled: "#6D7A89",
    },
    divider: "#CDD4D4",
    background: {
      default: "#E9F3F3",
      paper: "#fefefe",
    },
  },
  components: {
    MuiButton: {
      defaultProps: {
        disableElevation: true,
        variant: "contained",
      },
    },
  },
});

export default theme;

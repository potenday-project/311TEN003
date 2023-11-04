import { createTheme } from "@mui/material";
import Pretendard from "~/assets/font/Pretendard";

const theme = createTheme({
  typography: {
    fontFamily: Pretendard.style.fontFamily,
    allVariants: { letterSpacing: "-0.01em", fontWeight: 400 },
    h1: {
      fontSize: "24px",
      lineHeight: "40px",
      letterSpacing: "0em",
    },
    h2: {
      fontSize: "22px",
      lineHeight: "32px",
    },
    subtitle1: {
      fontSize: "20px",
      lineHeight: "32px",
    },
    subtitle2: {
      fontSize: "18px",
      lineHeight: "24px",
    },
    body1: {
      fontSize: "16px",
      lineHeight: "18px",
    },
    button: {
      fontSize: "16px",
      lineHeight: "16px",
    },
    label: {
      fontSize: "14px",
      lineHeight: "16px",
      letterSpacing: "0em",
    },
    caption: {
      fontSize: "12px",
      lineHeight: "16px",
    },

    fontWeightRegular: 400,
    fontWeightBold: 700,

    h4: undefined,
    h3: undefined,
    h5: undefined,
    h6: undefined,
    body2: undefined,
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

declare module "@mui/material/styles" {
  interface TypographyVariants {
    label: React.CSSProperties;
  }

  // allow configuration using `createTheme`
  interface TypographyVariantsOptions {
    label?: React.CSSProperties;
  }
}

// Update the Typography's variant prop options
declare module "@mui/material/Typography" {
  interface TypographyPropsVariantOverrides {
    label: true;
    h3: false;
    h4: false;
    h5: false;
    h6: false;
    body2: false;
  }
}

export default theme;

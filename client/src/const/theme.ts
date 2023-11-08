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
    },
    label: {
      fontSize: "14px",
      lineHeight: "16px",
      letterSpacing: "0em",
    },
    caption1: {
      fontSize: "12px",
      lineHeight: "16px",
    },
    caption2: {
      fontSize: "10px",
      lineHeight: "12px",
      letterSpacing: "0em",
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
      main: "#7B1FA2",
      contrastText: "#fff",
    },
    secondary: {
      main: "#C2185B",
    },
    text: {
      primary: "#1C1C1C",
      secondary: "#8A8A8A",
      disabled: "#B8B8B8",
    },
    divider: "#CDD4D4",
    background: {
      default: "#F5F5F5",
      paper: "#fefefe",
    },
    info: {
      main: "#00C853",
    },
    error: {
      main: "#D50000",
    },
    warning: {
      main: "#FFD600",
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
    caption1: React.CSSProperties;
    caption2: React.CSSProperties;
  }

  // allow configuration using `createTheme`
  interface TypographyVariantsOptions {
    label?: React.CSSProperties;
    caption1?: React.CSSProperties;
    caption2?: React.CSSProperties;
  }
}

// Update the Typography's variant prop options
declare module "@mui/material/Typography" {
  interface TypographyPropsVariantOverrides {
    label: true;
    caption1: true;
    caption2: true;
    h3: false;
    h4: false;
    h5: false;
    h6: false;
    caption: false;
    body2: false;
  }
}

export default theme;

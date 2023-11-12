import type { Metadata, Viewport } from "next";
import ThemeRegistry from "@/components/theme/ThemeRegistry";
import { nameOfApp, oneLineMessage } from "@/const/brand";
import OverrideCSS from "@/const/overrideCSS";
import { Box, GlobalStyles } from "@mui/material";
import Pretendard from "~/assets/font/Pretendard";
import NavigationBar from "~/components/NavigationBar";
import "./globals.css";
import CustomQueryClientProvider from "@/components/queryClient/CustomQueryClientProvider";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import GlobalLoadingPopup from "./../components/GlobalLoadingPopup";

export const metadata: Metadata = {
  title: `${nameOfApp} | ${oneLineMessage}`,
  manifest: "/manifest.json",
  icons: {
    icon: "/favicon.ico",
  },
};

export const viewport: Viewport = {
  themeColor: "black",
};

interface RootLayoutInterface {
  children: React.ReactNode;
  Modal: React.ReactNode;
}

export default function RootLayout({ children, Modal }: RootLayoutInterface) {
  return (
    <html lang="kr" className={Pretendard.className}>
      <body>
        <CustomQueryClientProvider>
          <ThemeRegistry options={{ key: "mui" }}>
            <GlobalStyles styles={OverrideCSS} />
            <GlobalLoadingPopup />
            {Modal}
            <Box
              sx={{
                maxHeight: "calc(100vh - 56px)",
                overflow: "auto",
              }}
            >
              {children}
            </Box>
            <NavigationBar />
          </ThemeRegistry>
          <ReactQueryDevtools initialIsOpen={false} />
        </CustomQueryClientProvider>
      </body>
    </html>
  );
}

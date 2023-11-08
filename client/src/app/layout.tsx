import type { Metadata, Viewport } from "next";
import ThemeRegistry from "@/components/theme/ThemeRegistry";
import { nameOfApp, oneLineMessage } from "@/const/brand";
import OverrideCSS from "@/const/overrideCSS";
import { Box, GlobalStyles } from "@mui/material";
import Pretendard from "~/assets/font/Pretendard";
import NavigationBar from "~/components/NavigationBar";
import "./globals.css";
import MSWInit from "@/components/mock/MSWInit";
import CustomQueryClientProvider from "@/components/queryClient/CustomQueryClientProvider";

export const metadata: Metadata = {
  title: `${nameOfApp} | ${oneLineMessage}`,
  manifest: "/manifest.json",
};

export const viewport: Viewport = {
  themeColor: "black",
};

export default function RootLayout({
  children,
  Modal,
}: {
  children: React.ReactNode;
  Modal: React.ReactNode;
}) {
  return (
    <html lang="kr" className={Pretendard.className}>
      <body>
        <CustomQueryClientProvider>
          <ThemeRegistry options={{ key: "mui" }}>
            {Modal}
            <GlobalStyles styles={OverrideCSS} />
            <Box
              sx={{
                maxHeight: "calc(100vh - 56px)",
                overflow: "auto",
              }}
            >
              <MSWInit>{children}</MSWInit>
            </Box>
            <NavigationBar />
          </ThemeRegistry>
        </CustomQueryClientProvider>
      </body>
    </html>
  );
}

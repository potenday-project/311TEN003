import type { Metadata, Viewport } from "next";
import ThemeRegistry from "@/components/theme/ThemeRegistry";
import { nameOfApp, oneLineMessage } from "@/const/brand";
import OverrideCSS from "@/const/overrideCSS";
import { GlobalStyles } from "@mui/material";
import Pretendard from "@/assets/font/Pretendard";

export const metadata: Metadata = {
  title: `${nameOfApp} | ${oneLineMessage}`,
  manifest: "/manifest.json",
};

export const viewport: Viewport = {
  themeColor: "black",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="kr" className={Pretendard.className}>
      <body>
        <ThemeRegistry options={{ key: "mui" }}>
          <GlobalStyles styles={OverrideCSS} />
          {children}
        </ThemeRegistry>
      </body>
    </html>
  );
}
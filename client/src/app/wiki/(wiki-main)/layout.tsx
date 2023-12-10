"use client";
import WikiAppbar from "@/components/wiki/WikiAppbar";
import { ReactNode, useState } from "react";
import WikiPageContext from "@/store/wiki/WikiPageContext";
import WikiSearchDrawer from "@/components/wiki/searchDrawer/WikiSearchDrawer";
import CustomContainer from "@/components/layout/CustomContainer";

const layout = ({ children }: { children: ReactNode }) => {
  const [isSearching, setIsSearching] = useState(false);

  return (
    <WikiPageContext.Provider value={{ isSearching, setIsSearching }}>
      <WikiAppbar />
      <CustomContainer>
        <WikiSearchDrawer />
        {children}
      </CustomContainer>
    </WikiPageContext.Provider>
  );
};

export default layout;

"use client";
import { Paper, Container } from "@mui/material";
import WikiAppbar from "@/components/wiki/WikiAppbar";
import { ReactNode, useState } from "react";
import WikiPageContext from "@/store/wiki/WikiPageContext";
import WikiSearchDrawer from "@/components/wiki/searchDrawer/WikiSearchDrawer";

const layout = ({ children }: { children: ReactNode }) => {
  const [isSearching, setIsSearching] = useState(false);

  return (
    <WikiPageContext.Provider value={{ isSearching, setIsSearching }}>
      <Paper>
        <WikiAppbar />
        <Container sx={{ p: { xs: 0, sm: 4 } }} maxWidth={"lg"}>
          <Paper
            sx={{
              display: "flex",
              position: "relative",
              flexDirection: "column",
              gap: 2,
              p: 2,
            }}
          >
            <WikiSearchDrawer />
            {children}
          </Paper>
        </Container>
      </Paper>
    </WikiPageContext.Provider>
  );
};

export default layout;

import { Paper, Container } from "@mui/material";
import WikiAppbar from "@/components/wiki/WikiAppbar";
import { ReactNode } from "react";

const layout = ({ children }: { children: ReactNode }) => {
  return (
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
          {children}
        </Paper>
      </Container>
    </Paper>
  );
};

export default layout;

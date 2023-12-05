"use client";

import SignupPageContext from "@/store/auth/SignupPageContext";
import { SignupRequirement } from "@/types/auth/signupRequirement";
import { Container, Paper } from "@mui/material";
import { ReactNode, useState } from "react";

type layoutProps = {
  children: ReactNode;
};

const layout = ({ children }: layoutProps) => {
  const [disableBtn, setDisableBtn] = useState(false);
  const [formData, setFormData] = useState<SignupRequirement>({
    id: "",
    email: "",
    password: "",
    nickname: "",
  });
  return (
    <SignupPageContext.Provider value={{ formData, setFormData, disableBtn, setDisableBtn }}>
      <Container sx={{ px: { xs: 0, sm: 4 } }} maxWidth={"lg"}>
        <Paper
          sx={{
            display: "flex",
            flexDirection: "column",
            minHeight: "calc(100vh - 56px)",
            overflowX:'hidden'
          }}
        >
          {children}
        </Paper>
      </Container>
    </SignupPageContext.Provider>
  );
};

export default layout;

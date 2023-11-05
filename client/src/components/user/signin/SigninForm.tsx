"use client";
import useLogin from "@/hooks/useLogin";
import { Box, Button, TextField } from "@mui/material";

import { useState } from "react";

const SigninForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const handleSubmit = useLogin();

  return (
    <Box
      component="form"
      onSubmit={(event) => {
        event.preventDefault();
        handleSubmit({ email, password });
      }}
      sx={{ mt: 1 }}
    >
      <TextField
        value={email}
        onChange={({ target }) => setEmail(target.value)}
        margin="normal"
        required
        fullWidth
        id="email"
        label="이메일 / 아이디"
        name="email"
        autoComplete="email"
        autoFocus
      />
      <TextField
        value={password}
        onChange={({ target }) => setPassword(target.value)}
        margin="normal"
        required
        fullWidth
        name="password"
        label="비밀번호"
        type="password"
        id="password"
        autoComplete="current-password"
      />
      <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
        Sign In
      </Button>
    </Box>
  );
};

export default SigninForm;

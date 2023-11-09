"use client";
import HOME from "@/const/clientPath";
import useLoginMutation from "@/queries/auth/useLoginMutation";
import errorHandler from "@/utils/errorHandler";
import { Box, Button, TextField, Typography } from "@mui/material";
import { useRouter } from "next/navigation";

import { useState } from "react";

const SigninForm = () => {
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");

  const router = useRouter();
  const { mutate: loginHandler, isError } = useLoginMutation();

  return (
    <Box
      component="form"
      onSubmit={(event) => {
        event.preventDefault();
        if (!id || !password) {
          return;
        }
        try {
          loginHandler({ id, password });
          router.push(HOME);
        } catch {
          errorHandler("로그인에 실패했습다다");
        }
      }}
      sx={{ mt: 1 }}
    >
      <TextField
        value={id}
        onChange={({ target }) => setId(target.value)}
        margin="normal"
        required
        fullWidth
        id="id"
        label="아이디"
        name="id"
        autoComplete="id"
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
      {isError && (
        <Typography variant="caption1" color="error">
          아이디 혹은 비밀번호를 확인해주세요
        </Typography>
      )}
      <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
        Sign In
      </Button>
    </Box>
  );
};

export default SigninForm;

"use client";
import useLoginMutation from "@/queries/auth/useLoginMutation";
import { Box, Button, TextField, Typography } from "@mui/material";
import { useRouter } from "next/navigation";
import { useState } from "react";
import HOME from "@/const/clientPath";

const SigninForm = () => {
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");
  const { mutateAsync: loginMutation, isError } = useLoginMutation();
  const router = useRouter();

  return (
    <Box
      component="form"
      onSubmit={async (event) => {
        event.preventDefault();
        if (!id || !password) {
          return;
        }
        await loginMutation({ id, password });
        router.push(HOME)
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

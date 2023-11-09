"use client";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";

import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { SIGNIN } from "@/const/clientPath";
import Link from "next/link";
import { ChangeEvent, useState } from "react";
import { SignupRequirement } from "@/types/auth/signupRequirement";
import useSignupMutation from "@/queries/auth/useSignupMutation";

export default function SignUpPage() {
  const [formData, setFormData] = useState<SignupRequirement>({
    id: "",
    email: "",
    password: "",
    nickname: "",
  });
  const changeHandler = ({
    target,
  }: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setFormData((prev) => ({ ...prev, [target.name]: target.value }));
  };
  const { mutate: submitHandler } = useSignupMutation();

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <Box
        sx={{
          marginTop: 8,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography variant="h1">회원가입</Typography>
        <Box
          component="form"
          onSubmit={(e) => {
            e.preventDefault();
            submitHandler(formData);
          }}
          noValidate
          sx={{ mt: 3 }}
        >
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                required
                autoFocus
                fullWidth
                id="email"
                label="이메일"
                name="email"
                autoComplete="email"
                onChange={(e) => changeHandler(e)}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                name="password"
                label="비밀번호"
                type="password"
                id="password"
                onChange={(e) => changeHandler(e)}
                autoComplete="new-password"
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                name="id"
                required
                fullWidth
                id="id"
                onChange={(e) => changeHandler(e)}
                label="유저 아이디"
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                fullWidth
                id="nickname"
                label="닉네임"
                onChange={(e) => changeHandler(e)}
                name="nickname"
              />
            </Grid>
            <Grid item xs={12}>
              <FormControlLabel
                control={<Checkbox value="allowExtraEmails" color="primary" />}
                label="유용한 정보를 받아볼게요."
              />
            </Grid>
          </Grid>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            회원가입
          </Button>
          <Grid container justifyContent="flex-end">
            <Grid item>
              <Link href={SIGNIN}>
                <Typography variant="label">
                  Already have an account?{" "}
                  <Typography
                    variant="label"
                    sx={{ color: "primary", fontWeight: "bold" }}
                  >
                    로그인 하러가기
                  </Typography>
                </Typography>
              </Link>
            </Grid>
          </Grid>
        </Box>
      </Box>
    </Container>
  );
}

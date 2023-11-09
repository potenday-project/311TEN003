import { Avatar, Box, Grid, Paper, Typography } from "@mui/material";
import SigninForm from "@/components/user/signin/SigninForm";
import Link from "next/link";
import { SIGNUP, FORGOTPASSWORD } from "@/const/clientPath";
import { Metadata } from "next";
import { nameOfApp } from "@/const/brand";
import { LockOutlined as LockOutlinedIcon } from "@mui/icons-material";

export const metadata: Metadata = {
  title: `${nameOfApp} | 로그인`,
};

const LoginPage = () => {
  return (
    <Grid container component="main" sx={{ height: "100vh" }}>
      <Grid
        item
        xs={false}
        sm={4}
        md={7}
        sx={{
          backgroundImage: "url(https://source.unsplash.com/random?wallpapers)",
          backgroundRepeat: "no-repeat",
          backgroundSize: "cover",
          backgroundPosition: "center",
        }}
      />
      <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
        <Box
          sx={{
            my: 8,
            mx: 4,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          {/* heading */}
          <Heading />
          {/* form */}
          <SigninForm />
          {/* CTA 하단 비밀번호 재설정, 회원가입 */}
          <CTA />
        </Box>
      </Grid>
    </Grid>
  );
};

const CTA = () => (
  <Grid container>
    <Grid item xs>
      <Link href={FORGOTPASSWORD}>
        <Typography variant="label">비밀번호 재설정</Typography>
      </Link>
    </Grid>
    <Grid item>
      <Typography variant="label">
        계정이 없으신가요?{" "}
        <Link href={SIGNUP}>
          <Typography
            variant="label"
            color="primary"
            sx={{ fontWeight: "bold" }}
          >
            회원가입
          </Typography>
        </Link>
      </Typography>
    </Grid>
  </Grid>
);

const Heading = () => (
  <>
    <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
      <LockOutlinedIcon />
    </Avatar>
    <Typography component="h1" variant="h1">
      {nameOfApp}
    </Typography>
  </>
);



export default Object.assign(LoginPage, { SigninForm, CTA, Heading });
"use client";

import {
  AppBar,
  Avatar,
  Box,
  ButtonBase,
  Container,
  Paper,
  TextField,
  Toolbar,
  Typography,
} from "@mui/material";
import GoBackIcon from "@/assets/icons/GoBackIcon.svg";
import InputSearchIcon from "@/assets/icons/InputSearchIcon.svg";
import AlcholeSearchIcon from "@/assets/icons/AlcholeSearchIcon.svg";
import { useRouter } from "next/navigation";
import { ChangeEvent, useState } from "react";
import { useUserInfoQuery } from "@/queries/auth/useUserInfoQuery";

export default function NewpostPage() {
  const router = useRouter();
  const [formValue, setFormValue] = useState({
    alcoholNo: "",
    alcoholFeature: "",
    postContent: "",
    postType: "BASIC",
    positionInfo: "",
    tagList: ["string"],
  });
  const changeHadler = ({
    target,
  }: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setFormValue((prev) => ({ ...prev, [target.name]: target.value }));
  };

  return (
    <>
      <AppBar position={"static"}>
        <Toolbar sx={{ display: "flex", justifyContent: "space-between" }}>
          <ButtonBase onClick={() => router.back()}>
            <GoBackIcon></GoBackIcon>
          </ButtonBase>
          <Typography variant="subtitle2" fontWeight={"bold"}>
            포스팅
          </Typography>
          <Typography variant="subtitle2" color={"primary.main"}>
            공유
          </Typography>
        </Toolbar>
      </AppBar>
      <Container sx={{ px: { xs: 0, sm: 4 } }} maxWidth={"lg"}>
        <Paper
          sx={{
            minHeight: "calc(100vh - 112px)",
            display: "flex",
            flexDirection: "column",
            gap: 2,
            p: 2,
          }}
          component="form"
        >
          <TextField
            placeholder="검색어를 입력해주세요"
            autoFocus
            InputProps={{
              startAdornment: (
                <AlcholeSearchIcon style={{ marginRight: "8px" }} />
              ),
              endAdornment: <InputSearchIcon />,
            }}
          />

          <Box>
            <TextField
              id="filled-multiline-flexible"
              placeholder="입력해주세요"
              multiline
              name={"postContent"}
              onChange={changeHadler}
              value={formValue.postContent}
              rows={6}
            />
          </Box>
          <Typography variant="label" sx={{ textAlign: "right" }}>
            {formValue.postContent.length} /{" "}
            <Typography variant="label" color="primary.main" component="span">
              200자
            </Typography>
          </Typography>
        </Paper>
      </Container>
    </>
  );
}

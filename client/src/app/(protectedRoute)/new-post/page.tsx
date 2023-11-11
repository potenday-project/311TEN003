"use client";

import {
  AppBar,
  Box,
  Button,
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
import { ChangeEvent, useEffect, useState } from "react";
import { useUserInfoQuery } from "@/queries/auth/useUserInfoQuery";
import axios from "@/libs/axios";
import HOME from "@/const/clientPath";

export default function NewpostPage() {
  const [formValue, setFormValue] = useState({
    postContent: "",
    postType: "BASIC",
    positionInfo: "",
    tagList: [] as string[],
  });

  const changeHadler = ({
    target,
  }: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setFormValue((prev) => ({ ...prev, [target.name]: target.value }));
  };

  const token = localStorage.getItem("accessToken");

  const router = useRouter();

  const submitHandler = () => {
    let userId;
    let pk;
    axios
      .get("/user/me", { headers: { Authorization: token } })
      .then((res) => {
        userId = res.data.id;
      })
      .then(() => {
        axios
          .post(
            "/posts",
            { ...formValue },
            { headers: { Authorization: token } }
          )
          .then(({ data }) => {
            pk = data.postNo;
            const formData = new FormData();
            if (file) {
              formData.append("image", file);
            }
            axios
              .post(`/attach/resources/POST/${pk}`, formData, {
                headers: {
                  Authorization: token,
                  "Content-Type": "multipart/form-data",
                },
                transformRequest: [
                  function () {
                    return formData;
                  },
                ],
              })
              .then(() => {
                router.push(HOME);
              });
          });
      });
  };
  const [userTypedTag, setUserTypedTag] = useState<string>();
  const [file, setFile] = useState<File>();

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
          onSubmit={(e) => {
            e.preventDefault();
            submitHandler();
          }}
        >
          <TextField
            placeholder="지금 어떤 술을 마시고 있나요?"
            autoFocus
            name="positionInfo"
            InputProps={{
              startAdornment: (
                <AlcholeSearchIcon style={{ marginRight: "8px" }} />
              ),
              endAdornment: <InputSearchIcon />,
            }}
            onChange={changeHadler}
          />

          <TextField
            id="filled-multiline-flexible"
            placeholder="입력해주세요"
            multiline
            name={"postContent"}
            onChange={changeHadler}
            value={formValue.postContent}
            rows={6}
          />

          <Typography variant="label" sx={{ textAlign: "right" }}>
            {formValue.postContent.length} /{" "}
            <Typography variant="label" color="primary.main" component="span">
              200자
            </Typography>
          </Typography>
          {formValue.tagList.map((tag) => {
            return <Typography variant="label">{tag}</Typography>;
          })}
          <Box>
            <TextField
              onChange={({ target }) => setUserTypedTag(target.value)}
              value={userTypedTag}
            ></TextField>
            <Button
              onClick={(e) => {
                e.preventDefault();
                setFormValue((prev) => {
                  if (!userTypedTag) return prev;
                  return { ...prev, tagList: [...prev.tagList, userTypedTag] };
                });
                setUserTypedTag("");
              }}
            >
              태그 추가
            </Button>
          </Box>
          <Button component="label" variant="contained">
            Upload file
            <input
              id="image"
              type="file"
              accept="image/*"
              name="image"
              onChange={(e) => {
                if (e.target.files) {
                  setFile(e.target.files[0]);
                  let reader = new FileReader();
                  reader.readAsDataURL(e.target.files[0]);
                  reader.onloadend = () => {
                    // 2. 읽기가 완료되면 아래코드가 실행됩니다.
                    const base64 = reader.result;
                  };
                  // setFile(e.target.files[0]);
                }
              }}
            />
          </Button>
          <Button type="submit">작성하기</Button>
        </Paper>
      </Container>
    </>
  );
}

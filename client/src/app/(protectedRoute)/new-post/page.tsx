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
import { ChangeEvent, useEffect, useMemo, useState } from "react";
import axios from "@/libs/axios";
import HOME from "@/const/clientPath";
import CameraIcon from "@/assets/icons/CameraIcon.svg";
import PinIcon from "@/assets/icons/PinIcon.svg";

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
  const [userTypedTag, setUserTypedTag] = useState<string>("");
  const [file, setFile] = useState<File>();
  const [fileUrl, setFileUrl] = useState<string | ArrayBuffer | null>();

  useEffect(() => {
    if (!file) {
      return;
    }
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onloadend = () => setFileUrl(reader.result);
  }, [file]);

  return (
    <Paper>
      <AppBar position={"static"}>
        <Toolbar sx={{ display: "flex", justifyContent: "space-between" }}>
          <ButtonBase onClick={() => router.back()}>
            <GoBackIcon></GoBackIcon>
          </ButtonBase>
          <Typography variant="subtitle2" fontWeight={"bold"}>
            포스팅
          </Typography>
          <Typography
            onClick={submitHandler}
            variant="subtitle2"
            color={"primary.main"}
          >
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
            onChange={(e) => {
              e.target.value.length <= 200 && changeHadler(e);
            }}
            value={formValue.postContent}
            rows={6}
          />

          <Typography variant="label" sx={{ textAlign: "right" }}>
            {formValue.postContent.length} /{" "}
            <Typography variant="label" color="primary.main" component="span">
              200자
            </Typography>
          </Typography>
          <Box sx={{ display: "flex", gap: 1 }}>
            {formValue.tagList.map((tag) => {
              return <Typography variant="label">#{tag}</Typography>;
            })}
          </Box>
          <Box
            component="form"
            onSubmit={(e) => {
              e.preventDefault();
              setFormValue((prev) => {
                if (!userTypedTag) return prev;
                return { ...prev, tagList: [...prev.tagList, userTypedTag] };
              });
              setUserTypedTag("");
            }}
            sx={{ display: "flex", gap: 1 }}
          >
            <TextField
              onChange={({ target }) => setUserTypedTag(target.value)}
              value={userTypedTag}
              size="small"
              sx={{ flexShrink: 1 }}
            />
            <Button type="submit">태그 추가</Button>
          </Box>
          {fileUrl && (
            <Box
              sx={{
                backgroundImage: `url(${fileUrl})`,
                width: "100%",
                height: 142,
                borderRadius: 4,
                border: "1px solid",
                borderColor: "gray.secondary",
                backgroundRepeat: "no-repeat",
                backgroundSize: "cover",
                backgroundPosition: "center",
              }}
            />
          )}
          <Box sx={{ display: "flex", gap: 2 }}>
            <Box
              component={"label"}
              sx={{
                bgcolor: "#F5F5F5",
                border: "1px solid",
                borderColor: "#E6E6E6",
                width: 72,
                height: 72,
                borderRadius: 1.5,
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              <CameraIcon />
              <input
                name="image"
                style={{ display: "none" }}
                type="file"
                accept="image/*"
                onChange={(e) => {
                  if (e.target.files) {
                    setFile(e.target.files[0]);
                  }
                }}
              />
            </Box>
            <Box
              component={"label"}
              sx={{
                bgcolor: "#F5F5F5",
                border: "1px solid",
                borderColor: "#E6E6E6",
                width: 72,
                height: 72,
                borderRadius: 1.5,
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              <PinIcon />
            </Box>
          </Box>
        </Paper>
      </Container>
    </Paper>
  );
}

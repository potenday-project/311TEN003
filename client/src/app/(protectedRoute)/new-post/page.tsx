"use client";

import {
  AppBar,
  Box,
  Button,
  ButtonBase,
  Container,
  IconButton,
  Paper,
  TextField,
  Toolbar,
  Tooltip,
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
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import { useGlobalLoadingStore } from "@/store/useGlobalLoadingStore";

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
  const { setLoading } = useGlobalLoadingStore();
  const token = getTokenFromLocalStorage();

  const router = useRouter();

  const submitHandler = () => {
    let userId;
    let pk;
    setLoading(true);
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
              axios.post(`/attach/resources/POST/${pk}`, formData, {
                headers: {
                  Authorization: token,
                  "Content-Type": "multipart/form-data",
                },
                transformRequest: [
                  function () {
                    return formData;
                  },
                ],
              });
            }
            setLoading(false);
            router.push(HOME);
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
      {/* 최상단 앱바 */}
      <AppBar position={"static"}>
        <Toolbar sx={{ display: "flex", justifyContent: "space-between" }}>
          <IconButton onClick={() => router.back()}>
            <GoBackIcon></GoBackIcon>
          </IconButton>
          <Typography variant="subtitle2" fontWeight={"bold"}>
            포스팅
          </Typography>
          <Button onClick={submitHandler} variant="text" sx={{ minWidth: 40 }}>
            공유
          </Button>
        </Toolbar>
      </AppBar>

      <Container sx={{ p: { xs: 0, sm: 4 } }} maxWidth={"lg"}>
        <Paper
          sx={{
            display: "flex",
            flexDirection: "column",
            gap: 2,
            p: 2,
          }}
        >
          {/* 검색창 */}
          <TextField
            placeholder="지금 어떤 술을 마시고 있나요?"
            autoFocus
            name="positionInfo"
            size="small"
            InputProps={{
              startAdornment: <AlcholeSearchIcon />,
              endAdornment: <InputSearchIcon />,
            }}
            onChange={changeHadler}
            sx={{ px: 0 }}
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
            <Tooltip title="사진 첨부">
              <ButtonBase
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
              </ButtonBase>
            </Tooltip>
            <Tooltip title="위치 추가">
              <ButtonBase
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
              </ButtonBase>
            </Tooltip>
          </Box>
        </Paper>
      </Container>
    </Paper>
  );
}

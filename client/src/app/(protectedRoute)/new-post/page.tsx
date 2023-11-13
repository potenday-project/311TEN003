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

import { useRouter } from "next/navigation";
import { ChangeEvent, useCallback, useEffect, useState } from "react";
import HOME from "@/const/clientPath";
import CameraIcon from "@/assets/icons/CameraIcon.svg";
import PinIcon from "@/assets/icons/PinIcon.svg";
import { useGlobalLoadingStore } from "@/store/useGlobalLoadingStore";
import useNewPostMutation from "@/queries/newPost/useNewPostMutation";
import useNewAttachMutation from "@/queries/attach/useNewAttachMutation";
import { useInvalidatePostList } from "@/queries/post/useGetPostListInfiniteQuery";
import { useDeletePostMutation } from "@/queries/post/useDeletePostMutation";
import {
  NewPostRequestInterface,
  NewPostRequestAlCohol,
} from "@/types/newPost/NewPostInterface";
import SearchAlcoholInput from "@/components/newpost/SearchAlcoholInput";

export default function NewpostPage() {
  const { setLoading } = useGlobalLoadingStore();
  const router = useRouter();
  const invalidatePreviousPost = useInvalidatePostList();

  const [formValue, setFormValue] = useState<NewPostRequestInterface>({
    postContent: "",
    postType: "BASIC",
    positionInfo: "",
    tagList: [] as string[],
  });

  const [alcoholInfo, setAlcoholInfo] = useState<NewPostRequestAlCohol>();
  useEffect(() => {
    console.log(alcoholInfo);
  }, [alcoholInfo]);
  const [userTypedTag, setUserTypedTag] = useState<string>("");
  const [file, setFile] = useState<File>();
  const [fileUrl, setFileUrl] = useState<string | ArrayBuffer | null>();
  const [isSuccess, SetIsSuccess] = useState(false);

  useEffect(() => {
    if (!file) {
      return;
    }
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onloadend = () => setFileUrl(reader.result);
  }, [file]);

  const changeHadler = ({
    target,
  }: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setFormValue((prev) => ({ ...prev, [target.name]: target.value }));
  };

  const { mutateAsync: newPostHandler } = useNewPostMutation();
  const { mutateAsync: attachFileHandler } = useNewAttachMutation();
  const { mutateAsync: deletePostHandler } = useDeletePostMutation();

  const submitHandler = useCallback(async () => {
    setLoading(true);
    let postNo;
    try {
      const { postNo: res } = await newPostHandler({
        ...formValue,
        ...alcoholInfo,
      });
      postNo = res;
      if (file) {
        try {
          await attachFileHandler({
            file,
            url: { pk: postNo, type: "POST" },
          });
        } catch {
          deletePostHandler(postNo);
          return;
        }
      }
      invalidatePreviousPost();
      SetIsSuccess(true);
      router.push(HOME);
    } catch {
      return;
    } finally {
      setLoading(false);
    }
  }, [formValue, alcoholInfo, router, router, file]);

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
          <Button
            disabled={isSuccess}
            onClick={submitHandler}
            variant="text"
            sx={{ minWidth: 40 }}
          >
            공유
          </Button>
        </Toolbar>
      </AppBar>

      <Container sx={{ p: { xs: 0, sm: 4 } }} maxWidth={"lg"}>
        <Paper
          sx={{
            display: "flex",
            position: "relative",
            flexDirection: "column",
            gap: 2,
            p: 2,
          }}
        >
          {/* 검색창 */}
          <SearchAlcoholInput setAlcoholInfo={setAlcoholInfo} />
          {/* 내용 */}
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
            {formValue.postContent!.length} /{" "}
            <Typography variant="label" color="primary.main" component="span">
              200자
            </Typography>
          </Typography>
          <Box sx={{ display: "flex", gap: 1, flexWrap: "wrap" }}>
            {formValue.tagList!.map((tag) => {
              return (
                <Typography variant="label" key={tag}>
                  #{tag}
                </Typography>
              );
            })}
          </Box>
          <Box
            component="form"
            onSubmit={(e) => {
              e.preventDefault();
              setFormValue((prev) => {
                if (!userTypedTag || prev.tagList?.includes(userTypedTag)) {
                  setUserTypedTag("");
                  return prev;
                }
                return {
                  ...prev,
                  tagList: [...(prev?.tagList ?? []), userTypedTag],
                };
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
          {/* 파일 미리보기 */}
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
          {/* 버튼 그룹 */}
          <Box sx={{ display: "flex", gap: 2 }}>
            {/* 사진 */}
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
            {/* 위치 */}
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

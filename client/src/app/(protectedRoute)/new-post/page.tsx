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
import CustomAppbar from "@/components/CustomAppbar";
import SquareIconButton from "@/components/SquareIconButton";
import PreviewImageByURL from "@/components/PreviewImageByURL";

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

  const [alcoholNo, setAlcoholNo] =
    useState<NewPostRequestAlCohol["alcoholNo"]>();
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
        alcoholNo,
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
  }, [formValue, alcoholNo, router, file]);

  return (
    <Paper>
      {/* 최상단 앱바 */}
      <CustomAppbar
        title="포스팅"
        buttonTitle="공유"
        disableButton={isSuccess}
        onClickButton={submitHandler}
      />

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
          <SearchAlcoholInput setAlcoholNo={setAlcoholNo} />
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
          {/* 총길이 카운터 */}
          <Typography variant="label" sx={{ textAlign: "right" }}>
            {formValue.postContent!.length} /{" "}
            <Typography variant="label" color="primary.main" component="span">
              200자
            </Typography>
          </Typography>
          {/* 태그폼 */}
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
          {fileUrl && <PreviewImageByURL fileUrl={fileUrl} />}
          {/* 버튼 그룹 */}
          <Box sx={{ display: "flex", gap: 2 }}>
            {/* 사진 */}
            <Tooltip title="사진 첨부">
              <SquareIconButton
                component={"label"}
                iconComponent={<CameraIcon />}
              >
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
              </SquareIconButton>
            </Tooltip>
            {/* 위치 */}
            <Tooltip title="위치 추가">
              <SquareIconButton iconComponent={<PinIcon />} />
            </Tooltip>
          </Box>
        </Paper>
      </Container>
    </Paper>
  );
}

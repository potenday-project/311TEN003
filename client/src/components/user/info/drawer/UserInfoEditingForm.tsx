"use client";
import { useMyInfoQuery } from "@/queries/auth/useMyInfoQuery";
import {
  Stack,
  Avatar,
  Typography,
  TextField,
  Button,
  Badge,
} from "@mui/material";
import CameraIcon from "@/assets/icons/badge/CameraIcon.svg";
import { useCallback, useContext, useState } from "react";
import useNewAttachMutation from "@/queries/attach/useNewAttachMutation";
import useDeleteAttachMutation from "@/queries/attach/useDeleteAttachMutation";
import UserPageContext from "@/store/user/UserPageContext";
import CustomAppbar from "@/components/CustomAppbar";
import { useGlobalLoadingStore } from "./../../../../store/useGlobalLoadingStore";
import usePatchUserInfoMutation from "@/queries/user/usePatchUserInfoMutation";
import useRenderAsDataUrl from "@/hooks/useRenderAsDataUrl";
import SingleImageInput from "@/components/SingleImageInput";

const UserInfoEditingForm = () => {
  const { setIsEditing } = useContext(UserPageContext);

  const { isLoading, setLoading } = useGlobalLoadingStore();

  const { data } = useMyInfoQuery();

  const [introduction, setIntroduction] = useState(data?.introduction);

  const [file, setFile] = useState<File>();
  const fileUrl = useRenderAsDataUrl(file);

  const { mutateAsync: attachFile } = useNewAttachMutation();
  const { mutateAsync: removeFile } = useDeleteAttachMutation();
  const { mutateAsync: patchUserInfo } = usePatchUserInfoMutation();

  const submitFileHandler = useCallback(
    async (file: File) => {
      if (!data) {
        return;
      }
      await attachFile({
        file: file,
        url: { type: "PROFILE", pk: Number(data?.userNo) },
      });
    },
    [data]
  );

  const submitHandler = async () => {
    setLoading(true);
    await patchUserInfo({ introduction });
    if (file) {
      data?.profileImages.forEach(async (profile) => {
        await removeFile(String(profile.attachNo));
      });
      await submitFileHandler(file);
    }
    setIsEditing(false);
    setLoading(false);
  };

  return (
    <>
      <CustomAppbar
        title={"프로필 수정"}
        prependButton={"취소"}
        onClickPrepend={() => setIsEditing(false)}
        onClickAppend={submitHandler}
        appendButton={"저장"}
        disableAppend={isLoading}
      />
      <Stack
        component="form"
        alignItems="center"
        gap={6}
        width="100%"
        minHeight="70vh"
      >
        <label style={{ cursor: "pointer" }}>
          <Badge
            anchorOrigin={{
              vertical: "bottom",
              horizontal: "right",
            }}
            color="primary"
            badgeContent={<CameraIcon />}
          >
            <Avatar
              src={(fileUrl as string) ?? data?.profileImages?.[0]?.attachUrl}
              sx={{ width: 80, height: 80, border: "1px solid #ccc" }}
            />
          </Badge>
          <SingleImageInput onChange={(file) => setFile(file)} />
        </label>
        <Stack gap={2} width="100%">
          <Stack direction="row" width="100%">
            <Typography fontWeight="bold" width={120}>
              {"닉네임"}
            </Typography>
            <TextField
              disabled
              value={data?.nickname ?? ""}
              helperText={"닉네임은 수정할 수 없어요"}
              size="small"
              fullWidth
              autoComplete="off"
            />
          </Stack>
          <Stack direction="row" width="100%">
            <Typography fontWeight="bold" width={120}>
              {"자기소개"}
            </Typography>
            <TextField
              value={introduction ?? ""}
              onChange={({ target }) => {
                setIntroduction(target.value);
              }}
              helperText={
                "나에 대해 소개해보세요, 좋아하는 술, 술의 맛, 음식 뭐든 좋아요"
              }
              size="small"
              fullWidth
              autoComplete="off"
              multiline
              rows={3}
            />
          </Stack>
        </Stack>
        <Button disabled={isLoading} fullWidth onClick={submitHandler}>
          프로필 저장하기
        </Button>
      </Stack>
    </>
  );
};

export default UserInfoEditingForm;

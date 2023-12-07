"use client";

import { Box, Container, Paper, Tooltip } from "@mui/material";

import { useRouter } from "next/navigation";
import { useCallback, useState } from "react";
import HOME from "@/const/clientPath";
import PictureIcon from "@/assets/icons/PictureIcon.svg";
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
import NewPostTextEditor from "@/components/newpost/NewPostTextEditor";
import useRenderAsDataUrl from "@/hooks/useRenderAsDataUrl";
import SingleImageInput from "@/components/SingleImageInput";
import { POST_IMAGE_SIZE } from "@/const/imageSize";

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

  const [file, setFile] = useState<File>();
  const fileUrl = useRenderAsDataUrl(file);

  const [isSuccess, SetIsSuccess] = useState(false);

  const { mutateAsync: newPostHandler } = useNewPostMutation();
  const { mutateAsync: attachFileHandler } = useNewAttachMutation();
  const { mutateAsync: deletePostHandler } = useDeletePostMutation();

  const submitHandler = useCallback(
    async (formValue: NewPostRequestInterface, file?: File) => {
      setLoading(true);
      let postNo;
      try {
        const { postNo: res } = await newPostHandler(formValue);
        postNo = res;
        if (file) {
          try {
            await attachFileHandler({
              file,
              url: { pk: postNo, type: "POST" },
              size: POST_IMAGE_SIZE,
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
    },
    [router]
  );

  return (
    <Paper>
      {/* 최상단 앱바 */}
      <CustomAppbar
        title="포스팅"
        appendButton="공유"
        disableAppend={isSuccess}
        onClickAppend={() => submitHandler({ ...formValue, alcoholNo }, file)}
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
          <NewPostTextEditor
            onContentChange={({ content, tagList }) =>
              setFormValue((prev) => ({
                ...prev,
                postContent: content,
                tagList,
              }))
            }
          />
          {/* 파일 미리보기 */}
          {fileUrl && <PreviewImageByURL fileUrl={fileUrl} />}
          {/* 버튼 그룹 */}
          <Box sx={{ display: "flex", gap: 2 }}>
            {/* 사진 */}
            <Tooltip title="사진 첨부">
              <SquareIconButton
                component={"label"}
                iconComponent={<PictureIcon />}
              >
                <SingleImageInput onChange={(file) => setFile(file)} />
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

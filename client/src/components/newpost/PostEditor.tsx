import { Box, Tooltip } from "@mui/material";
import SquareIconButton from "@/components/SquareIconButton";
import PreviewImageByURL from "@/components/PreviewImageByURL";
import NewPostTextEditor from "@/components/newpost/NewPostTextEditor";
import SearchAlcoholInput from "@/components/newpost/SearchAlcoholInput";
import PictureIcon from "@/assets/icons/PictureIcon.svg";
import PinIcon from "@/assets/icons/PinIcon.svg";
import SingleImageInput from "@/components/SingleImageInput";
import { useEffect, useState } from "react";
import useRenderAsDataUrl from "@/hooks/useRenderAsDataUrl";
import { NewPostRequestInterface } from "@/types/newPost/NewPostInterface";
import { AlcoholDetailInterface } from "@/types/alcohol/AlcoholInterface";

interface PostEditorProps {
  initialContent?: string;
  initialAlcohol?: Pick<
    AlcoholDetailInterface,
    "alcoholName" | "alcoholNo" | "alcoholType"
  >;
  initialImage?: string;
  /**
   * formvalue와 file 을 인자로 넘겨주는 커스텀이벤트핸들러
   * @param props {formvalue,file}
   * @returns
   */
  onFormChange: (formValue: NewPostRequestInterface) => void;
  onFileChange: (file?: File) => void;
}

const PostEditor = ({
  initialContent,
  initialAlcohol,
  initialImage,
  onFormChange,
  onFileChange,
}: PostEditorProps) => {
  const [formValue, setFormValue] = useState<NewPostRequestInterface>({
    postType: "BASIC",
    positionInfo: "",
    tagList: [] as string[],
  });

  useEffect(() => {
    onFormChange(formValue);
  }, [formValue]);

  const [file, setFile] = useState<File>();

  useEffect(() => {
    onFileChange(file);
  }, [file]);

  const fileUrl = useRenderAsDataUrl(file) ?? initialImage;

  return (
    <>
      {/* 검색창 */}
      <SearchAlcoholInput
        initialValue={initialAlcohol}
        setAlcoholNo={(alcoholNo) =>
          setFormValue((prev) => ({ ...prev, alcoholNo }))
        }
      />
      {/* 내용 */}
      <NewPostTextEditor
        onContentChange={({ content, tagList }) =>
          setFormValue((prev) => ({
            ...prev,
            postContent: content,
            tagList,
          }))
        }
        initialValue={initialContent}
      />
      {/* 파일 미리보기 */}
      {(fileUrl) && (
        <PreviewImageByURL fileUrl={fileUrl} />
      )}
      {/* 버튼 그룹 */}
      <Box sx={{ display: "flex", gap: 2 }}>
        {/* 사진 */}
        <Tooltip title="사진 첨부">
          <SquareIconButton component={"label"} iconComponent={<PictureIcon />}>
            <SingleImageInput
              onChange={(file) => {
                setFile(file);
              }}
            />
          </SquareIconButton>
        </Tooltip>
        {/* 위치 */}
        <Tooltip title="위치 추가">
          <SquareIconButton iconComponent={<PinIcon />} />
        </Tooltip>
      </Box>
    </>
  );
};

export default PostEditor;

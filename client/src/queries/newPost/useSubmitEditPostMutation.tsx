import { POST_IMAGE_SIZE } from "@/const/imageSize";
import useNewAttachMutation from "@/queries/attach/useNewAttachMutation";
import { useGlobalSnackbarStore } from "@/store/useGlobalSnackbarStore";
import { NewPostRequestInterface } from "@/types/newPost/NewPostInterface";
import { UseMutationOptions, useMutation } from "@tanstack/react-query";
import useEditPostMutation from "./useEditPostMutation";
import useDeleteAttachMutation from "../attach/useDeleteAttachMutation";

const useSubmitEditPostMutation = (
  options?: UseMutationOptions<
    void,
    Error,
    {
      formData: NewPostRequestInterface;
      file?: File | undefined;
    },
    void
  >
) => {
  const { mutateAsync: editPostHandler } = useEditPostMutation();
  const { mutateAsync: attachFileHandler } = useNewAttachMutation();
  const { mutateAsync: deleteFileHandler } = useDeleteAttachMutation();

  const fireToast = useGlobalSnackbarStore(({ fireToast }) => fireToast);

  return useMutation({
    mutationFn: async ({
      postNo,
      formData,
      file,
      prevFileNo,
    }: {
      postNo: string;
      formData: NewPostRequestInterface;
      file?: File;
      prevFileNo?: string;
    }) => {
      if (!formData.postContent?.length) {
        fireToast("내용을 입력해주세요");
        return;
      }
      // 게시글 수정시도
      try {
        await editPostHandler({ pk: postNo, formData });
      } catch (error) {
        console.error("게시글수정 실패:", error);
        return;
      }
      // 게시글 수정 성공시 파일 수정시도
      if (file) {
        try {
          await handleFileAttachments(file, postNo, prevFileNo);
        } catch (error) {
          console.error("파일처리 실패:", error);
        }
      }
    },
    ...options,
  });

  async function handleFileAttachments(
    file: File,
    postNo: string,
    prevFileNo?: string
  ) {
    try {
      await attachFileHandler({
        file,
        url: { pk: Number(postNo), type: "POST" },
        size: POST_IMAGE_SIZE,
      });
    } catch (error) {
      console.log("파일 첨부에 실패:", error);
    }

    if (prevFileNo) {
      await deleteFileHandler(prevFileNo);
    }
  }
};

export default useSubmitEditPostMutation;

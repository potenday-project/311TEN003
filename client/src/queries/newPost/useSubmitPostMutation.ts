import { POST_IMAGE_SIZE } from "@/const/imageSize";
import useNewAttachMutation from "@/queries/attach/useNewAttachMutation";
import useNewPostMutation from "@/queries/newPost/useNewPostMutation";
import { useDeletePostMutation } from "@/queries/post/useDeletePostMutation";
import { useGlobalSnackbarStore } from "@/store/useGlobalSnackbarStore";
import { NewPostRequestInterface } from "@/types/newPost/NewPostInterface";
import { UseMutationOptions, useMutation } from "@tanstack/react-query";

/**
 * 파일, 포스트내용을 입력받아 서버에 제출하는 트렌젝션을 수행하는 뮤테이션,
 * @param Options mutate, success, settle 시 실행될 함수
 * @returns 파일, 포스트내용을 입력받아 제출트렌젝션을 수행하는 함수
 */
const useSubmitPostMutation = (
  options?: UseMutationOptions<
    void,
    Error,
    {
      formValue: NewPostRequestInterface;
      file?: File | undefined;
    },
    void
  >
) => {
  const { mutateAsync: newPostHandler } = useNewPostMutation();
  const { mutateAsync: attachFileHandler } = useNewAttachMutation();
  const { mutateAsync: deletePostHandler } = useDeletePostMutation();
  const fireToast = useGlobalSnackbarStore(({ fireToast }) => fireToast);

  return useMutation({
    mutationFn: async ({
      formValue,
      file,
    }: {
      formValue: NewPostRequestInterface;
      file?: File;
    }) => {
      if (!formValue.postContent?.length) {
        fireToast("내용을 입력해주세요");
        return;
      }
      let postNo;
      try {
        const { postNo: res } = await newPostHandler(formValue);
        postNo = res;
      } catch (error) {
        console.log("게시글 처리 실패 처리 실패", error);
        return;
      }
      if (file) {
        try {
          await fileHandler(file, postNo);
        } catch (error) {
          console.log("파일 처리 실패", error);
        }
      }
    },
    ...options,
  });

  async function fileHandler(file: File, postNo: number) {
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
};

export default useSubmitPostMutation;

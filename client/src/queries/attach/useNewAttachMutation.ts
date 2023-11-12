import { useMutation } from "@tanstack/react-query";
import { axiosPrivate } from "@/libs/axios";
import { ATTACH_FILE, ATTACH_FILE_ResourceType } from "@/const/serverPath";

export const useNewAttachMutation = () => {
  return useMutation({
    mutationFn: async (param: { file: File; url: NewAttatchRequestUrl }) =>
      await postImageFn(param.file, param.url),
  });
};

interface NewAttatchRequestUrl {
  type: ATTACH_FILE_ResourceType;
  pk: number;
}
/**
 * [Post] 파일을 업로드하는 axios요청
 * @param file 파일
 * @param param1 {type: "POST" | "PROFILE" | "ALCOHOL", pk : 게시글 PK}
 * @returns 에셋 PK
 */
export const postImageFn = async (
  file: File,
  { type, pk }: NewAttatchRequestUrl
) => {
  const formData = new FormData();
  formData.append("image", file);

  const { data } = await axiosPrivate.post<{ attachNo: number }>(
    ATTACH_FILE(type, pk),
    formData,
    {
      headers: {
        "Content-Type": "multipart/form-data",
      },
      transformRequest: [
        function () {
          return formData;
        },
      ],
    }
  );
  return data;
};

export default useNewAttachMutation;

import { useMutation, useQueryClient } from "@tanstack/react-query";
import { axiosPrivate } from "@/libs/axios";
import { ATTACH_FILE, ATTACH_FILE_ResourceType } from "@/const/serverPath";
import { useErrorHandler } from "./../../utils/errorHandler";
import { getPostListInfiniteQueryKey } from "./../post/useGetPostListInfiniteQuery";
import { postDetailQueryKey } from "../post/useGetPostDetailQuery";
import { MyInfoQueryKeys } from "../auth/useMyInfoQuery";
import { UserInfoQueryKey } from "../user/useUserInfoQuery";

export const useNewAttachMutation = () => {
  const errorHandler = useErrorHandler();
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (param: { file: File; url: NewAttatchRequestUrl }) =>
      await postImageFn(param.file, param.url),
    onMutate: (variables) => {
      return variables;
    },
    onSuccess: (_data, variables, context) => {
      switch (variables.url.type) {
        case "POST":
          queryClient.invalidateQueries({
            queryKey: [getPostListInfiniteQueryKey.all],
          });
          queryClient.invalidateQueries({
            queryKey: [postDetailQueryKey.byId(String(context?.url.pk))],
          });
          return;
        case "PROFILE":
          queryClient.invalidateQueries({ queryKey: MyInfoQueryKeys.all });
          queryClient.invalidateQueries({
            queryKey: UserInfoQueryKey.byId(String(context?.url.pk)),
          });
          queryClient.invalidateQueries({
            queryKey: getPostListInfiniteQueryKey.byKeyword({
              userNo: String(context?.url.pk),
            }),
          });
        case "ALCOHOL":
      }
    },
    onError: (error) => {
      errorHandler(error);
    },
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

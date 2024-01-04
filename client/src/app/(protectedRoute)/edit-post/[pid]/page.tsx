"use client";

import { useRouter } from "next/navigation";
import { useState } from "react";
import HOME from "@/const/clientPath";
import { useGlobalLoadingStore } from "@/store/useGlobalLoadingStore";
import { useInvalidatePostList } from "@/queries/post/useGetPostListInfiniteQuery";
import { NewPostRequestInterface } from "@/types/newPost/NewPostInterface";

import CustomAppbar from "@/components/layout/CustomAppbar";
import CustomContainer from "@/components/layout/CustomContainer";
import useSubmitEditPostMutation from "@/queries/newPost/useSubmitEditPostMutation";
import PostEditor from "@/components/newpost/PostEditor";
import useGetPostDetailQuery, {
  useInvalidatePostDetail,
} from "@/queries/post/useGetPostDetailQuery";

export default function EditPostPage({ params }: { params: { pid: string } }) {
  const { setLoading } = useGlobalLoadingStore();

  const router = useRouter();
  const invalidatePreviousPost = useInvalidatePostList();
  const invalidatePostDetail = useInvalidatePostDetail();

  const { data: initialData } = useGetPostDetailQuery(params.pid);
  const {
    alcoholName,
    alcoholNo,
    alcoholType,
    postContent,
    postAttachUrls,
    postNo,
  } = initialData;

  const [formData, setFormData] = useState<NewPostRequestInterface>();
  const [file, setFile] = useState<File>();

  const { mutateAsync: submitHandler, isSuccess } = useSubmitEditPostMutation({
    onMutate: () => setLoading(true),
    onSuccess: () => {
      invalidatePreviousPost();
      invalidatePostDetail(String(postNo));
      router.push(HOME);
    },
    onSettled: () => setLoading(false),
  });

  return (
    <>
      {/* 최상단 앱바 */}
      <CustomAppbar
        title="포스팅"
        appendButton="공유"
        disableAppend={isSuccess}
        onClickAppend={() =>
          formData &&
          submitHandler({
            postNo: String(initialData.postNo),
            formData,
            file,
            prevFileNo: initialData.postAttachUrls?.[0]?.attachNo,
          })
        }
      />
      <CustomContainer>
        <PostEditor
          onFormChange={(formData) => setFormData(formData)}
          onFileChange={(file) => setFile(file)}
          initialAlcohol={
            alcoholName ? { alcoholName, alcoholNo, alcoholType } : undefined
          }
          initialContent={postContent}
          initialImage={(postAttachUrls ?? [])[0]?.attachUrl}
        />
      </CustomContainer>
    </>
  );
}

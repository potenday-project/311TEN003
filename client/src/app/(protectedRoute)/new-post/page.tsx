"use client";

import { useRouter } from "next/navigation";
import { useState } from "react";
import HOME from "@/const/clientPath";
import { useGlobalLoadingStore } from "@/store/useGlobalLoadingStore";
import { useInvalidatePostList } from "@/queries/post/useGetPostListInfiniteQuery";
import { NewPostRequestInterface } from "@/types/newPost/NewPostInterface";

import CustomAppbar from "@/components/layout/CustomAppbar";
import CustomContainer from "@/components/layout/CustomContainer";
import useSubmitPostMutation from "@/queries/newPost/useSubmitPostMutation";
import PostEditor from "@/components/newpost/PostEditor";

export default function NewpostPage() {
  const { setLoading } = useGlobalLoadingStore();

  const router = useRouter();
  const invalidatePreviousPost = useInvalidatePostList();

  const [formValue, setFormValue] = useState<NewPostRequestInterface>();
  const [file, setFile] = useState<File>();

  const { mutateAsync: submitHandler, isSuccess } = useSubmitPostMutation({
    onMutate: () => setLoading(true),
    onSuccess: () => {
      invalidatePreviousPost();
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
        onClickAppend={() => formValue && submitHandler({ formValue, file })}
      />
      <CustomContainer>
        <PostEditor onFormChange={setFormValue} onFileChange={setFile} />
      </CustomContainer>
    </>
  );
}

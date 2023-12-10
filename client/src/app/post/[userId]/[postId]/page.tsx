"use server";
import PostDetail from "@/components/post/detail/PostDetail";
import { getPostDetailQueryFn } from "@/queries/post/useGetPostDetailQuery";
import getTokenFromCookies from "@/utils/getTokenFromCookies";
import { redirect } from "next/navigation";
import CustomAppbar from "@/components/layout/CustomAppbar";
import CustomContainer from "@/components/layout/CustomContainer";

const PostDetailPage = async ({ params }: { params: { postId: string } }) => {
  const parsedPostId = params.postId;
  const token = await getTokenFromCookies();
  let initialData;
  try {
    initialData = await getPostDetailQueryFn(parsedPostId, {
      Authorization: token,
    });
  } catch {
    redirect("/not-found");
  }
  return (
    <>
      <CustomAppbar title={`${initialData.nickname}님의 리뷰`} />
      <CustomContainer sx={{ pb: "132px" }}>
        <PostDetail postNo={parsedPostId} initialData={initialData} />
      </CustomContainer>
    </>
  );
};

export default PostDetailPage;

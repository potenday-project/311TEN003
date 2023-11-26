"use server";
import PostDetail from "@/components/post/PostDetail";
import { getPostDetailQueryFn } from "@/queries/post/useGetPostDetailQuery";
import getTokenFromCookies from "@/utils/getTokenFromCookies";
import { redirect } from "next/navigation";

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
  return <PostDetail postNo={parsedPostId} initialData={initialData} />;
};

export default PostDetailPage;

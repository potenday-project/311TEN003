"use server";
import PostDetail from "@/components/post/PostDetail";
import { getPostDetailQueryFn } from "@/queries/post/useGetPostDetailQuery";
import { redirect } from "next/navigation";

const PostDetailPage = async ({ params }: { params: { postId: string } }) => {
  const parsedPostId = params.postId;
  let initialData;
  try {
    initialData = await getPostDetailQueryFn(parsedPostId);
  } catch {
    redirect("/not-found");
  }
  return <PostDetail postNo={parsedPostId} initialData={initialData} />;
};

export default PostDetailPage;

"use server";
import PostDetail from "@/components/post/PostDetail";
import { getPostDetailQueryFn } from "@/queries/post/useGetPostDetailQuery";
import { redirect } from "next/navigation";

const PostDetailPage = async ({ ...context }) => {
  const parsedPostId = context.params.postId;
  try {
    const initialData = await getPostDetailQueryFn(parsedPostId);
    return <PostDetail postNo={parsedPostId} initialData={initialData} />;
  } catch {
    redirect("/not-found");
  }
};

export default PostDetailPage;

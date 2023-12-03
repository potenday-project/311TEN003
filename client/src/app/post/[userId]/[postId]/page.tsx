"use server";
import PostDetail from "@/components/post/detail/PostDetail";
import { getPostDetailQueryFn } from "@/queries/post/useGetPostDetailQuery";
import getTokenFromCookies from "@/utils/getTokenFromCookies";
import { Paper, Container } from "@mui/material";
import { redirect } from "next/navigation";
import CustomAppbar from "@/components/CustomAppbar";

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
      <Container sx={{ px: { xs: 0, sm: 4 }, pb: "132px" }} maxWidth={"lg"}>
        <Paper
          sx={{
            display: "flex",
            position: "relative",
            flexDirection: "column",
          }}
        >
          <PostDetail postNo={parsedPostId} initialData={initialData} />
        </Paper>
      </Container>
    </>
  );
};

export default PostDetailPage;

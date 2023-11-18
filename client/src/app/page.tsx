"use server";
import PostCardList from "@/components/post/PostCardList";
import { getPostListQueryFn } from "@/queries/post/useGetPostListInfiniteQuery";
import getTokenFromCookies from "@/utils/getTokenFromCookies";
import { Container, Paper } from "@mui/material";

export default async function Home() {
  const accessToken = await getTokenFromCookies()
  
  const initialData = await getPostListQueryFn({
    page: 0,
    size: 10,
    headers: { Authorization: accessToken },
  });

  return (
    <Container sx={{ px: { xs: 0, sm: 4 } }} maxWidth={"lg"}>
      <Paper
        sx={{
          minHeight: "calc(100vh - 56px)",
          display: "flex",
          flexDirection: "column",
        }}
      >
        <PostCardList initialData={initialData} />
      </Paper>
    </Container>
  );
}

"use server";
import PostCardList from "@/components/post/PostCardList";
import { getPostListQueryFn } from "@/queries/post/useGetPostListInfiniteQuery";
import { Container, Paper } from "@mui/material";
import { cookies } from "next/headers";

export default async function Home() {
  const accessToken = cookies().get("accessToken")?.value;
  
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

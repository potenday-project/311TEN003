
import PostCardList from "@/components/post/PostCardList";
// import { getPostListQueryFn } from "@/queries/post/useGetPostListQuery";
import { Container } from "@mui/material";
import { Suspense } from "react";

export default function Home() {
  // const initialData = await getPostListQueryFn();
  return (
    <Container sx={{ px: { xs: 0, sm: 4 } }} maxWidth={"lg"}>
      <Suspense fallback={<>로딩중</>}>
        {/* FIXME */}{/* @ts-ignore*/}
        <PostCardList
        // initialData={initialData}
        />
      </Suspense>
    </Container>
  );
}

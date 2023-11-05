import PostCardList from "@/components/post/PostCardList";
import { Container } from "@mui/material";

export default function Home() {
  return (
    <Container sx={{ px: { xs: 0, sm: 4 } }} maxWidth={"lg"}>
      <PostCardList />
    </Container>
  );
}

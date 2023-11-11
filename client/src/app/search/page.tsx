"use server";
import SearchArea from "@/components/search/SearchArea";
import { getPostListQueryFn } from "@/queries/post/useGetPostListInfiniteQuery";
import { Container } from "@mui/material";

const SearchPage = async ({
  searchParams,
}: {
  searchParams?: { [key: string]: string | undefined };
}) => {
  const initialData = await getPostListQueryFn({
    searchKeyword: searchParams?.keyword,
  });
  return (
    <Container sx={{ px: { xs: 0, sm: 4 } }} maxWidth={"lg"}>
      <SearchArea
        initialData={initialData}
        searchKeyword={searchParams?.keyword}
      />
    </Container>
  );
};

export default SearchPage;

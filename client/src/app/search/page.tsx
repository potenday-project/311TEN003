"use server";
import SearchArea from "@/components/search/SearchArea";
import { getPostListQueryFn } from "@/queries/post/useGetPostListInfiniteQuery";
import getTokenFromCookies from "@/utils/getTokenFromCookies";
import { Container } from "@mui/material";
import { cookies } from "next/headers";

const SearchPage = async ({
  searchParams,
}: {
  searchParams?: { [key: string]: string | undefined };
}) => {
  const accessToken = await getTokenFromCookies()
  const initialData = await getPostListQueryFn({
    searchKeyword: searchParams?.keyword,
    headers: { Authorization: accessToken },
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

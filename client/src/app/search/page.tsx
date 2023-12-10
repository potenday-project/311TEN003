"use server";
import SearchArea from "@/components/search/SearchArea";
import { getPostListQueryFn } from "@/queries/post/useGetPostListInfiniteQuery";
import getTokenFromCookies from "@/utils/getTokenFromCookies";

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
    <>
      <SearchArea
        initialData={initialData}
        searchKeyword={searchParams?.keyword}
      />
    </>
  );
};

export default SearchPage;

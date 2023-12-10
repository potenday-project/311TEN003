"use server";
import CustomContainer from "@/components/layout/CustomContainer";
import PostCardList from "@/components/post/PostCardList";
import { getPostListQueryFn } from "@/queries/post/useGetPostListInfiniteQuery";
import getTokenFromCookies from "@/utils/getTokenFromCookies";

export default async function Home() {
  const accessToken = await getTokenFromCookies();

  const initialData = await getPostListQueryFn({
    page: 0,
    size: 10,
    headers: { Authorization: accessToken },
  });

  return (
    <CustomContainer>
      <PostCardList initialData={initialData} />
    </CustomContainer>
  );
}

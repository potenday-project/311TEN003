"use server";

import MainPagePostList from "@/components/post/MainPagePostList";
import { getPostListQueryFn } from "@/queries/post/useGetPostListInfiniteQuery";
import getTokenFromCookies from "@/utils/getTokenFromCookies";

export default async function Home() {
  const accessToken = await getTokenFromCookies();

  const initialData = await getPostListQueryFn({
    page: 0,
    size: 10,
    headers: { Authorization: accessToken },
  });

  return <MainPagePostList initialData={initialData} />;
}

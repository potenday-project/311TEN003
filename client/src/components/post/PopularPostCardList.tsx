"use client";

import PostCard from "@/components/post/PostCard";
import useGetPopularPostListInfiniteQuery, {
  UseGetPopularPostListQueryInterface,
} from "@/queries/post/useGetPopularPostListInfiniteQuery";
import { useInView } from "react-intersection-observer";
import { useEffect } from "react";
import { Stack } from "@mui/material";
import { useMemo } from "react";
import Image from "next/image";
import NoResult from "@/assets/images/noResult.png";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import PostCardSkeleton from "./PostCardSkeleton";

function PopularPostCardList(props: UseGetPopularPostListQueryInterface) {
  const {
    data,
    fetchNextPage,
    isFetchingNextPage,
    hasNextPage,
    isSuccess,
    isLoading,
  } = useGetPopularPostListInfiniteQuery({
    ...props,
    headers: { Authorization: getTokenFromLocalStorage() },
  });

  const { ref, inView } = useInView();
  useEffect(() => {
    if (hasNextPage && inView) fetchNextPage();
  }, [inView, hasNextPage]);

  const hasResult = useMemo(
    () => data && data.pages[0].content.length > 0,
    [data]
  );

  return (
    <div>
      {hasResult &&
        isSuccess &&
        // 검색결과가 있을시
        data?.pages.map((page) =>
          page.content.map((post) => <PostCard {...post} key={post.postNo} />)
        )}
      {isSuccess && !hasResult && (
        // 검색결과 없을 시
        <Stack justifyContent="center" alignItems="center" py={8}>
          <Image src={NoResult} alt="no result alert" />
        </Stack>
      )}
      {/* 로딩창 */}
      {isFetchingNextPage || isLoading ? (
        <PostCardSkeleton />
      ) : (
        // 인터섹션옵저버
        hasNextPage && <div style={{ height: 60 }} ref={ref}></div>
      )}
    </div>
  );
}

export default PopularPostCardList;

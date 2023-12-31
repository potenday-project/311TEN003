"use client";

import PostCard from "@/components/post/PostCard";
import useGetPostListInfiniteQuery, {
  UseGetPostListQueryInterface,
} from "@/queries/post/useGetPostListInfiniteQuery";
import { useInView } from "react-intersection-observer";
import { useEffect } from "react";
import { Stack } from "@mui/material";
import { useMemo } from "react";
import Image from "next/image";
import NoResult from "@/assets/images/noResult.png";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import { postcardContext } from "@/store/post/PostCardContext";
import PostCardSkeleton from "./PostCardSkeleton";

function PostCardList({
  searchAlcoholNos,
  searchKeyword,
  searchUserNos,
  sort,
  ...props
}: UseGetPostListQueryInterface) {
  const {
    data,
    fetchNextPage,
    isFetchingNextPage,
    hasNextPage,
    isSuccess,
    isLoading,
  } = useGetPostListInfiniteQuery({
    // 검색중이 아니면서 AlcoholNos 가 있는 경우에만 AlcoholNo로 검색
    searchAlcoholNos:
      searchKeyword === "" && searchAlcoholNos ? searchAlcoholNos : undefined,
    sort,
    searchUserNos,
    searchKeyword: searchKeyword !== "" ? searchKeyword : undefined,
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
    <postcardContext.Provider value={{ searchKeyword, searchUserNos, sort }}>
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
    </postcardContext.Provider>
  );
}

export default PostCardList;

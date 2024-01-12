"use client";

import PostCard from "@/components/post/PostCard";
import useGetPostListInfiniteQuery, {
  UseGetPostListQueryInterface,
} from "@/queries/post/useGetPostListInfiniteQuery";

import { memo, useEffect, useRef } from "react";
import { useMemo } from "react";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import { postcardContext } from "@/store/post/PostCardContext";
import PostCardSkeleton from "./PostCardSkeleton";

import { useVirtualizer } from "@tanstack/react-virtual";

function VirtualizedPostCardList({
  height,
  width,
  searchAlcoholNos,
  searchKeyword,
  searchUserNos,
  sort,
  ...props
}: UseGetPostListQueryInterface & { width: number; height: number }) {
  const { data, fetchNextPage, isFetchingNextPage, hasNextPage, isSuccess } =
    useGetPostListInfiniteQuery({
      searchAlcoholNos:
        searchKeyword === "" && searchAlcoholNos ? searchAlcoholNos : undefined,
      sort,
      searchUserNos,
      searchKeyword: searchKeyword !== "" ? searchKeyword : undefined,
      ...props,
      headers: { Authorization: getTokenFromLocalStorage() },
    });

  const allRows = data ? data.pages.flatMap(({ content }) => content) : [];

  const parentRef = useRef<HTMLDivElement | null>(null);

  const rowVirtualizer = useVirtualizer({
    count: allRows.length,
    getScrollElement: () => parentRef.current,
    estimateSize: () => 550,
    initialRect: { width, height },
    overscan: 5,
  });

  const hasResult = useMemo(
    () => data && data.pages[0].content.length > 0,
    [data]
  );

  const loadMoreItems = () => {
    const [lastItem] = [...rowVirtualizer.getVirtualItems()].reverse();

    if (
      lastItem &&
      lastItem.index >= allRows.length - 1 &&
      hasNextPage &&
      !isFetchingNextPage
    ) {
      fetchNextPage();
    }
  };

  useEffect(loadMoreItems, [
    hasNextPage,
    fetchNextPage,
    allRows.length,
    isFetchingNextPage,
    rowVirtualizer.getVirtualItems(),
  ]);

  return (
    <postcardContext.Provider value={{ searchKeyword, searchUserNos, sort }}>
      <div>
        {hasResult && isSuccess && (
          <div
            ref={parentRef}
            style={{
              height: `${height}px`,
              width: `${width}px`,
              overflowY: "auto",
              contain: "strict",
            }}
          >
            <div
              style={{
                height: `${rowVirtualizer.getTotalSize()}px`,
                width: "100%",
                position: "relative",
              }}
            >
              {rowVirtualizer.getVirtualItems().map((virtualRow) => {
                const post = allRows[virtualRow.index];
                const isLoaderRow = virtualRow.index > allRows.length - 1;

                return (
                  <div
                    key={virtualRow.index}
                    data-index={virtualRow.index}
                    ref={rowVirtualizer.measureElement}
                    style={{
                      position: "absolute",
                      top: 0,
                      left: 0,
                      width: "100%",
                      transform: `translateY(${virtualRow.start}px)`,
                    }}
                  >
                    {isLoaderRow ? (
                      hasNextPage && <PostCardSkeleton />
                    ) : (
                      <PostCard {...post} />
                    )}
                  </div>
                );
              })}
            </div>
          </div>
        )}
      </div>
    </postcardContext.Provider>
  );
}

export default memo(VirtualizedPostCardList);

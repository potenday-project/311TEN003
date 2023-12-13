"use client";

import FollowUserCard from "@/components/user/followList/FollowUserCard";
import useFollowingUserInfiniteQuery from "@/queries/user/useFollowingUserInfiniteQuery";
import { useEffect } from "react";
import { useInView } from "react-intersection-observer";
import FollowingUserCardSkeleton from "@/components/user/followList/FollowingUserCardSkeleton";
import ComponentRepeater from "@/components/ComponentRepeater";

const FollowingList = () => {
  const { data, isFetchingNextPage, hasNextPage, fetchNextPage } =
    useFollowingUserInfiniteQuery();
  const { ref, inView } = useInView();

  useEffect(() => {
    if (hasNextPage && inView) fetchNextPage();
  }, [inView, hasNextPage]);

  return (
    <>
      {data.pages.map((page) =>
        page.content.map(({ nickname, id, introduction, profileImgUrls, userNo }) => (
          <FollowUserCard
            key={id}
            nickName={nickname}
            userId={id}
            userPk={userNo}
            imageUrl={profileImgUrls[0]?.attachUrl}
            content={introduction}
          />
        ))
      )}
      {isFetchingNextPage ? (
        <ComponentRepeater count={5}>
          <FollowingUserCardSkeleton />
        </ComponentRepeater>
      ) : (
        // 인터섹션옵저버
        hasNextPage && <div style={{ height: 60 }} ref={ref}></div>
      )}
    </>
  );
};

export default FollowingList;

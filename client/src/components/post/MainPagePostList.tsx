"use client";
import { AugmentedGetPostListResponse } from "@/queries/post/useGetPostListInfiniteQuery";
import PostCardList from "@/components/post/PostCardList";

import CustomContainer from "@/components/layout/CustomContainer";
import { useState } from "react";
import CustomToggleButtonGroup from "@/components/CustomToggleButtonGroup";
import PopularPostCardList from "./PopularPostCardList";

type Props = {
  initialData: AugmentedGetPostListResponse;
};

const MainPagePostList = ({ initialData }: Props) => {
  const selectableList = ["전체 캐스크", "인기"];
  const [currentView, setCurrentView] = useState(selectableList[0]);

  return (
    <>
      <CustomToggleButtonGroup
        value={selectableList}
        onChange={setCurrentView}
        sx={{ position: "fixed", top: 0, left: 0, right: 0, zIndex: 1 }}
      />
      <CustomContainer mt={5}>
        {currentView === "전체 캐스크" && (
          <PostCardList initialData={initialData} />
        )}
        {currentView === "인기" && <PopularPostCardList />}
      </CustomContainer>
    </>
  );
};

export default MainPagePostList;

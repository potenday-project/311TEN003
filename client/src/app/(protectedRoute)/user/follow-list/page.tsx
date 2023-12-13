"use client";

import { Box } from "@mui/material";
import CustomToggleButtonGroup from "@/components/CustomToggleButtonGroup";
import { appbarHeight } from "@/const/uiSizes";
import { Suspense, useState } from "react";
import FollowingList from "@/components/user/followList/FollowingList";
import FollowingUserCardSkeleton from "@/components/user/followList/FollowingUserCardSkeleton";
import ComponentRepeater from "@/components/ComponentRepeater";
import FollowerList from "@/components/user/followList/FollowerList";

const FollowListPage = () => {
  const selectableList = ["팔로잉", "팔로워"];
  const [currentView, setCurrentView] = useState(selectableList[0]);

  return (
    <>
      <CustomToggleButtonGroup
        value={selectableList}
        onChange={setCurrentView}
        sx={{ position: "fixed", top: appbarHeight, left: 0, right: 0 }}
      />
      {/* Fixed로 빠진 button 위치만큼의 place holder */}
      <Box height={26} />
      <Suspense
        fallback={
          <ComponentRepeater count={5}>
            <FollowingUserCardSkeleton />
          </ComponentRepeater>
        }
      >
        {currentView === "팔로잉" && <FollowingList /> }
        {currentView === "팔로워" && <FollowerList /> }
      </Suspense>
    </>
  );
};

export default FollowListPage;

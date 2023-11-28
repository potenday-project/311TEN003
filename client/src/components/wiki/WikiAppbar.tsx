"use client";
import CustomAppbar from "@/components/CustomAppbar";
import SearchIcon from "@/assets/icons/SearchIcon.svg";
import { memo, useContext } from "react";
import WikiPageContext from "@/store/wiki/WikiPageContext";

const WikiAppbar = () => {
  const { setIsSearching } = useContext(WikiPageContext);
  return (
    <CustomAppbar
      title="술백과"
      buttonComponent={<SearchIcon />}
      onClickButton={() => setIsSearching(true)}
    />
  );
};

export default memo(WikiAppbar);

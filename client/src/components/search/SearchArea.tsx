"use client";
import { Paper, TextField } from "@mui/material";
import React, { useState, useMemo } from "react";
import PostCardList from "@/components/post/PostCardList";
import { AugmentedGetPostListResponse } from "@/queries/post/useGetPostListInfiniteQuery";
import useDebounce from "@/hooks/useDebounce";
import InputSearchIcon from "~/assets/icons/InputSearchIcon.svg";
import { motion } from "framer-motion";

type Props = {
  initialData: AugmentedGetPostListResponse;
  searchKeyword?: string;
};

const SearchArea = ({ initialData, searchKeyword }: Props) => {
  const [keyword, setKeyword] = useState(searchKeyword ?? "");
  const debouncedValue = useDebounce(keyword, 300);
  const MemoidInitailData = useMemo(() => initialData, []);

  return (
    <Paper
      sx={{
        minHeight: "calc(100vh - 56px)",
        display: "flex",
        flexDirection: "column",
      }}
    >
      <motion.div
        style={{ display: "flex", flexDirection: "column", overflow: "hidden" }}
        initial={{ height: 0 }}
        animate={{ height: "auto" }}
      >
        <TextField
          label="검색어를 입력해주세요"
          autoFocus
          value={keyword}
          onChange={({ target }) => setKeyword(target.value)}
          sx={{ m: 2 }}
          InputProps={{
            endAdornment: <InputSearchIcon />,
          }}
        />
      </motion.div>
      <PostCardList
        initialData={!keyword ? MemoidInitailData : undefined}
        searchKeyword={debouncedValue}
      />
    </Paper>
  );
};

export default SearchArea;

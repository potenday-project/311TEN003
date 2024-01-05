"use client";
import { Box, TextField } from "@mui/material";
import React, { useState, useMemo } from "react";
import PostCardList from "@/components/post/PostCardList";
import { AugmentedGetPostListResponse } from "@/queries/post/useGetPostListInfiniteQuery";
import useDebounce from "@/hooks/useDebounce";
import InputSearchIcon from "~/assets/icons/InputSearchIcon.svg";
import { motion } from "framer-motion";

type SearchAreaProps = {
  initialData: AugmentedGetPostListResponse;
  searchKeyword?: string;
  searchAlcoholNos?: number;
};

const SearchArea = ({
  initialData,
  searchKeyword,
  searchAlcoholNos,
}: SearchAreaProps) => {
  const [keyword, setKeyword] = useState(searchKeyword ?? "");
  const debouncedValue = useDebounce(keyword, 300);
  const MemoidInitailData = useMemo(() => initialData, []);

  return (
    <>
      <Box
        height={72}
        sx={{
          position: "fixed",
          top: 0,
          left: 0,
          right: 0,
          p: 2,
          zIndex: 2,
          backgroundColor: "background.paper",
        }}
      >
        <motion.div
          style={{
            display: "flex",
            flexDirection: "column",
            overflow: "hidden",
          }}
          initial={{ height: 0 }}
          animate={{ height: "auto" }}
        >
          <TextField
            placeholder="검색어를 입력해주세요"
            autoFocus
            value={keyword}
            size="small"
            onChange={({ target }) => setKeyword(target.value)}
            InputProps={{
              endAdornment: <InputSearchIcon />,
            }}
          />
        </motion.div>
      </Box>
      <PostCardList
        initialData={!keyword ? MemoidInitailData : undefined}
        searchKeyword={debouncedValue}
        searchAlcoholNos={searchAlcoholNos}
      />
    </>
  );
};

export default SearchArea;

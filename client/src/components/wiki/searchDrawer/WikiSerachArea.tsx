import { useEffect, useRef, useState } from "react";
import useDebounce from "@/hooks/useDebounce";
import InputSearchIcon from "@/assets/icons/InputSearchIcon.svg";
import { Stack, TextField } from "@mui/material";
import useGetAlcoholListQuery from "@/queries/alcohol/useGetAlcoholListQuery";
import AlcoholList from "@/components/wiki/AlcoholList";
import AlcoholListSkeleton from "../AlcoholListSkeleton";
import SearchHistory from "@/components/SearchHistory";
import { ALCOHOL_SEARCH_HISTORY } from "@/const/localstorageKey";

const WikiSerachArea = () => {
  const [searchKeyword, setSearchKeyword] = useState("");
  const debouncedValue = useDebounce(searchKeyword, 300);
  const { data: alcohols, isSuccess } = useGetAlcoholListQuery(debouncedValue);
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    inputRef.current?.focus();
  }, []);

  return (
    <>
      <TextField
        label="검색어를 입력해주세요"
        autoFocus
        autoComplete="off"
        value={searchKeyword}
        inputRef={inputRef}
        onChange={({ target }) => setSearchKeyword(target.value)}
        InputProps={{
          endAdornment: <InputSearchIcon />,
          sx: {
            borderRadius: "12px",
          },
        }}
      />
      <Stack gap={1} height={"232px"}>
        {searchKeyword ? (
          // 입력중인 경우
          <>
            {isSuccess ? (
              <AlcoholList data={alcohols.list} />
            ) : (
              <AlcoholListSkeleton />
            )}
          </>
        ) : (
          // 입력이 없는경우 검색기록 표출
          <SearchHistory
            onClick={() => console.log("눌림")}
            storageKey={ALCOHOL_SEARCH_HISTORY}
          />
        )}
      </Stack>
    </>
  );
};

export default WikiSerachArea;

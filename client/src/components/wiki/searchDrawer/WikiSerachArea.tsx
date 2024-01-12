import { useContext, useEffect, useRef, useState } from "react";
import useDebounce from "@/hooks/useDebounce";
import InputSearchIcon from "@/assets/icons/InputSearchIcon.svg";
import { Stack, TextField } from "@mui/material";
import useGetAlcoholListQuery from "@/queries/alcohol/useGetAlcoholListQuery";
import AlcoholList from "@/components/wiki/AlcoholList";
import SearchHistory from "@/components/SearchHistory";
import { ALCOHOL_SEARCH_HISTORY } from "@/const/localstorageKey";
import WikiPageContext from "@/store/wiki/WikiPageContext";
import usePushToWikiDetail from "@/hooks/wiki/usePushToWikiDetail";

const WikiSerachArea = () => {
  const { setIsSearching } = useContext(WikiPageContext);

  const [searchKeyword, setSearchKeyword] = useState("");
  const debouncedValue = useDebounce(searchKeyword, 300);
  const { data: alcohols } = useGetAlcoholListQuery({
    searchKeyword: debouncedValue,
  });

  const onClickElementHandler = usePushToWikiDetail();

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
          <AlcoholList
            data={alcohols?.content}
            onClickElement={(alcoholData) => {
              onClickElementHandler(alcoholData);
              setIsSearching(false);
            }}
          />
        ) : (
          // 입력이 없는경우 검색기록 표출
          <SearchHistory
            onClick={(keyword) => setSearchKeyword(keyword)}
            storageKey={ALCOHOL_SEARCH_HISTORY}
          />
        )}
      </Stack>
    </>
  );
};

export default WikiSerachArea;

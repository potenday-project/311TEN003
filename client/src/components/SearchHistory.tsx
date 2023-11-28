import { Button, Stack, StackProps, Typography } from "@mui/material";
import { useCallback, useState } from "react";
import XIcon from "@/assets/icons/XIcon.svg";

interface SearchHistoryProps extends Omit<StackProps, "onClick"> {
  storageKey: string;
  onClick: () => void;
}

const SearchHistory = ({ storageKey, onClick }: SearchHistoryProps) => {
  const getItems = useCallback(() => {
    return JSON.parse(localStorage.getItem(storageKey) ?? "[]") as string[];
  }, [storageKey]);

  const [searchHistory, setSearchHistory] = useState<string[]>(getItems());

  const removeAll = useCallback(() => {
    localStorage.setItem(storageKey, "[]");
    setSearchHistory(getItems());
  }, [storageKey]);

  const removeByKeyword = useCallback(
    (keyword: string) => {
      const filteredHistory = searchHistory.filter(
        (prevKeyword) => prevKeyword !== keyword
      );
      localStorage.setItem(storageKey, JSON.stringify(filteredHistory));
      setSearchHistory(getItems());
    },
    [storageKey]
  );

  return searchHistory.length > 0 ? (
    <>
      <Stack direction="row" justifyContent="space-between">
        <Typography variant="subtitle1" fontWeight="bold">
          최근 검색어
        </Typography>
        <Button onClick={removeAll} variant="text" sx={{ fontWeight: "bold" }}>
          전체 삭제
        </Button>
      </Stack>
      <Stack>
        {searchHistory.map((keyword) => (
          <Stack
            key={keyword}
            onClick={onClick}
            direction="row"
            justifyContent="space-between"
            alignItems="center"
          >
            <Typography variant="subtitle2">{keyword}</Typography>
            <Button
              onClick={(e) => {
                e.stopPropagation();
                removeByKeyword(keyword);
              }}
              variant="text"
              sx={{ justifyContent: "end" }}
            >
              <XIcon />
            </Button>
          </Stack>
        ))}
      </Stack>
    </>
  ) : (
    <></>
  );
};

export default SearchHistory;

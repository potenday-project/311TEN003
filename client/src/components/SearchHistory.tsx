import { Button, Stack, StackProps, Typography } from "@mui/material";
import XIcon from "@/assets/icons/XIcon.svg";
import { SearchHistoryKeyType } from "@/types/LocalStorageKey";
import useSearchHistory from "@/hooks/searchHistory/useSearchHistory";

interface SearchHistoryProps extends Omit<StackProps, "onClick"> {
  storageKey: SearchHistoryKeyType;
  onClick: (keyword: string) => void;
}

const SearchHistory = ({ storageKey, onClick }: SearchHistoryProps) => {
  const {
    state: searchHistory,
    removeAll,
    removeByKeyword,
  } = useSearchHistory(storageKey);

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
      <Stack component="ul">
        {searchHistory.map((keyword) => (
          <Stack
            key={keyword}
            component="li"
            onClick={() => onClick(keyword)}
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

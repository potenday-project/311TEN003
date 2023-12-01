import { SearchHistoryKeyType } from "@/types/LocalStorageKey";
import { useCallback, useEffect } from "react";
import useLocalStorage from "../localStorage/useLocalStorage";

/**
 * 로컬스토리지 키를 입력받아
 * 해당 스토리지를 바라보는 state를 리턴 (State 업데이트시 자동으로 반영)
 *
 * @param storageKey 로컬스토리지 키
 * @returns
 */
const useSearchHistory = (storageKey: SearchHistoryKeyType) => {
  const [searchHistory, setSearchHistory] =
    useLocalStorage<string[]>(storageKey);

  useEffect(() => {
    if (searchHistory === null) {
      setSearchHistory([]);
    }
  }, []);

  const removeAll = useCallback(() => {
    setSearchHistory([]);
  }, [storageKey]);

  const removeByKeyword = useCallback(
    (keyword: string) => {
      const filteredHistory = (searchHistory ?? []).filter(
        (prevKeyword) => prevKeyword !== keyword
      );
      setSearchHistory(filteredHistory);
    },
    [storageKey]
  );
  const addSearchHistory = useCallback(
    (keyword: string) => {
      setSearchHistory((prev) => {
        return [
          keyword,
          ...(prev ?? []).filter((prevKeyword) => prevKeyword !== keyword),
        ];
      });
    },
    [storageKey]
  );
  return {
    state: searchHistory ?? [],
    add: addSearchHistory,
    removeAll,
    removeByKeyword,
  };
};

export default useSearchHistory;

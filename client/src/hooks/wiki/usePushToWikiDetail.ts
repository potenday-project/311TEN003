import { AlcoholDetailInterface } from "@/types/alcohol/AlcoholInterface";
import { useRouter } from "next/navigation";
import { useCallback } from "react";
import useSearchHistory from "../searchHistory/useSearchHistory";
import { ALCOHOL_SEARCH_HISTORY } from "@/const/localstorageKey";
import { WIKI_DETAIL } from "@/const/clientPath";
/**
 * 검색히스토리에 해당 술을 남기고, 디테일페이지로 이동시키는 함수를 리턴하는 훅
 * @returns 해당 callback함수
 */
const usePushToWikiDetail = () => {
  const { add: addToSearchHistory } = useSearchHistory(ALCOHOL_SEARCH_HISTORY);
  const router = useRouter();
  /**
   * 검색히스토리에 해당 술을 남기고, 디테일페이지로 이동시키는 함수를 리턴하는 함수
   */
  const onClickElementHandler = useCallback(
    ({ alcoholName, alcoholNo }: AlcoholDetailInterface) => {
      addToSearchHistory(alcoholName);
      router.push(WIKI_DETAIL(String(alcoholNo)));
    },
    [addToSearchHistory]
  );
  return onClickElementHandler;
};

export default usePushToWikiDetail;

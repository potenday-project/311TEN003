import { createContext } from "react";

export interface PostcardContextInterface {
  searchKeyword?: string;
  searchUserNos?: string;
}
/**
 * 현재 보고있는 페이지의 컨텍스트 
 * (검색,유저페이지,메인페이지의 글 리스트가 같은 쿼리를 바라보고있으므로,
 * 적절한 Invalidation을 위한 쿼리키를 추출하기 위해 사용됨)
 */
export const postcardContext = createContext<PostcardContextInterface>({
  searchKeyword: undefined,
  searchUserNos: undefined,
});

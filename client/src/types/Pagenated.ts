/**
 * T를 입력받아 T를 리턴하는 페이지네이티드 서버 응답 인터페이스로 변환
 */
export default interface PageNated<T> {
  totalPages: number;
  totalElements: number;
  size: number;
  content: T[];
  number: number;
  sort: SortInterface;
  numberOfElements: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}

interface SortInterface {
  empty: boolean;
  sorted: boolean;
  unsorted: boolean;
  pageable: PageableInterface;
}

interface PageableInterface {
  offset: number;
  sort: {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
  };
  pageNumber: number;
  pageSize: number;
  paged: boolean;
  unpaged: boolean;
}

export interface PagenationParams {
  page?: number;
  size?: number;
  sort?: string;
}

const HOME = "/" as const;
/**
 * 로그인 페이지 라우트
 */
export const SIGNIN = "/auth/login" as const;
/**
 * 회원가입 페이지 라우트
 */
export const SIGNUP = "/auth/signup" as const;
/**
 * 비밀번호 재설정페이지 라우트
 */
export const FORGOTPASSWORD = "/auth/forgot-password" as const;
/**
 * 로그인 했을 경우만 접근 가능한 마이페이지
 */
export const MY_PROFILE = "/user" as const;
/**
 * 유저의 PK를 입력받아 해당유저의 프로필 페이지로 이동하는 URL
 */
export const USER_PAGE = (pk: string | number) => `${MY_PROFILE}/${pk}`;

/**
 * 유저가 팔로잉/팔로워 리스트페이지로 이동하는 라우트
 */
export const USER_FOLLOW_LIST = `${MY_PROFILE}/follow-list`

/**
 * 유저정보 세팅 페이지로 이동하는 라우트
 */
export const SETTING_PAGE = `${MY_PROFILE}/setting` as const

/**
 * 술과사전 페이지 라우트
 */
export const WIKI = "/wiki" as const;
/**
 * 술 PK 를 입력받아 술의 상세페이지로 이동하는 라우트
 * @param alcoholNo 술 PK
 */
export const WIKI_DETAIL = (alcoholNo: string) =>
  `${WIKI}/${alcoholNo}` as const;
/**
 * 검색 페이지 라우트
 */
export const SEARCH = "/search" as const;
/**
 * 키워드를 인자로 받아 쿼리스트링이 추가된 검색페이지 라우트
 * @param keyword
 * @returns
 */
export const SEARCH_BY_KEYWORD = (keyword: string) =>
  `${SEARCH}?keyword=${keyword}`;

/**
 * 유저아이디와 게시글 아이디를 입력받아 /post/@userId/postId 형태의 path를 리턴
 * @param userId 유저ID
 * @param postId 게시글ID
 * @returns
 */
export const POST_DETAIL = (userId: string, postId: string) => {
  const trimmedUserId = userId.trim();
  const trimmedPostId = postId.trim();

  return `/post/@${trimmedUserId}/${trimmedPostId}`;
};
/**
 * 새로운 포스트를 작성하는 페이지
 */
export const NEW_POST = "/new-post";

export default HOME;

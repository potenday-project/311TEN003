/**
 * 로그인 API Path
 */
export const LOGIN_API_PATH = "/user/login" as const;

/**
 * 회원가입 API Path
 */
export const SIGNUP_API_PATH = "/user/signup" as const;
/**
 * 내 정보를 받아오는 Path
 */
export const MY_INFO = "/user/me" as const;

/**
 * 유저정보를 수정하는 path
 */
export const PATCH_USER_INFO = "/user" as const;

/**
 * 쿠키를 심어주는 로그인 BFF
 */
export const LOGIN_BFF = "/api/auth/login" as const;
/**
 * 클라이언트단 로그인을 행하는 라우트
 */
export const LOGOUT_BFF = "/api/auth/logout-internal" as const;

/**
 * 게시물리스트를 받아오거나, 작성하는 Path
 */
export const POST_LIST = "/posts" as const;
/**
 * 게시물 pk 를 입력받아 댓글을 조회,생성 하는 URL
 */
export const POST_COMMENT = (pk: string) => `${POST_LIST}/${pk}/comments`;

/**
 * 게시글Pk와 댓글 PK를 입력받아 댓글 삭제를 요청하는 URL
 * @param postPk
 * @param commentPk
 * @returns
 */
export const DELETE_COMMENT = (postPk: string, commentPk: string) =>
  `${POST_LIST}/${postPk}/comments/${commentPk}`;

/**
 * 게시물리스트를 받아오거나, 작성하는 Path 버전2 (Breaking Change)
 */
export const POST_LIST_V2 = "/posts/v2" as const;
/**
 * ID(pk) 를 입력받아 해당 포스트를 지우는 URL
 */
export const REMOVE_POST = (pk: number) => `${POST_LIST}/${pk}` as const;
/**
 * 포스트의 PK를 입력받아 해당 PK의 게시글의 좋아요를 요청
 * @param id 게시글의 PK
 */
export const POST_LIKE_URL = (id: string) => `/posts/like/${id}` as const;
/**
 *
 * @param type : 리소스의 타입 POST|PROFILE|ALCOHOL
 * @param resourcePk 등록하고자하는 게시글의 PK
 * @returns
 */
export type ATTACH_FILE_ResourceType = "POST" | "PROFILE" | "ALCOHOL";
export const ATTACH_FILE = (
  type: ATTACH_FILE_ResourceType,
  resourcePk: number
) => `/${type}/${resourcePk}` as const;
// ) => `/attach/resources/${type}/${resourcePk}` as const;

/**
 * 파일PK 를 입력받아 해당 파일을 제거하는 URL
 * @param attachNo 파일 PK
 */
export const REMOVE_FILE = (attachNo: string) => `/attach/${attachNo}` as const;

/**
 * 알콜리스트를 받아오는 URL
 */
export const GET_ALCOHOL_LIST = "/alcohols" as const;

/**
 * 알콜 디테일을 받아오는 URL
 */
export const GET_ALCOHOL_DETAIL = (id: string) => `${GET_ALCOHOL_LIST}/${id}` as const;

/**
 * 포스트의 PK를 입력받아 해당 PK의 게시글의 좋아요 취소를 요청
 * @param id 게시글의 PK
 */
export const POST_UN_LIKE_URL = (id: string) =>
  `/posts/like-cancel/${id}` as const;
/**
 * 유저 ID 를 입력받아 해당 유저의 정보를 불러오는 URL
 * @param id 유저 PK
 * @returns
 */
export const USER_SUMMARY = (id: string) => `/user/${id}/summary` as const;

/**
 * 유저 ID 를 입력받아 해당 유저를 팔로우 하는 URL
 * @param id 유저 PK
 * @returns
 */
export const FOLLOW_USER = (id: string) => `/user/follow/${id}` as const;

/**
 * 유저 ID 를 입력받아 해당 유저를 언팔로우 하는 URL
 * @param id 유저 PK
 * @returns
 */
export const UNFOLLOW_USER = (id: string) => `/user/unfollow/${id}` as const;

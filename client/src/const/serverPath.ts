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
 * 쿠키를 심어주는 로그인 BFF
 */
export const LOGIN_BFF = "/api/auth/login" as const;

/**
 * 게시물리스트를 받아오거나, 작성하는 Path
 */
export const POST_LIST = "/posts" as const;
/**
 * ID(pk) 를 입력받아 해당 포스트를 지우는 URL
 */
export const REMOVE_POST = (pk:number)=>`${POST_LIST}/${pk}` as const

/**
 *
 * @param type : 리소스의 타입 POST|PROFILE|ALCOHOL
 * @param resourcePk 등록하고자하는 게시글의 PK
 * @returns
 */
export type ATTACH_FILE_ResourceType = "POST" | "PROFILE" | "ALCOHOL";
export const ATTACH_FILE = (type: ATTACH_FILE_ResourceType, resourcePk: number) =>
  `/attach/resources/${type}/${resourcePk}` as const;

/**
 * 알콜리스트를 받아오는 URL
 */
export const GET_ALCOHOL_LIST = '/alcohols' as const

/**
 * 포스트의 PK를 입력받아 해당 PK의 게시글의 좋아요를 요청
 * @param id 게시글의 PK
 */
export const POST_LIKE_URL = (id:string)=>`/posts/like/${id}` as const

/**
 * 포스트의 PK를 입력받아 해당 PK의 게시글의 좋아요 취소를 요청
 * @param id 게시글의 PK
 */
export const POST_UN_LIKE_URL = (id:string)=>`/posts/like-cancel/${id}` as const
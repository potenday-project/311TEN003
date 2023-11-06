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
export const MY_PROFILE = "/user" as const
/**
 * 술과사전 페이지 라우트
 */
export const WIKI = "/wiki" as const

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


export default HOME;

/**
 * 로그인 API Path
 */
export const LOGIN_API_PATH = '/user/login' as const

/**
 * 회원가입 API Path
 */
export const SIGNUP_API_PATH = '/user/signup' as const
/**
 * 내 정보를 받아오는 Path
 */
export const MY_INFO = '/user/me' as const

/**
 * 쿠키를 심어주는 로그인 BFF
 */
export const LOGIN_BFF = '/api/auth/login' as const
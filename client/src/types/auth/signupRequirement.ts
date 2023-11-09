export interface SignupRequirement {
  /**
   * 유저의 이메일 (회원가입 전용)
   */
  email: string;
  /**
   * 유저의 패스워드
   */
  password: string;
  /**
   * 유저의 ID
   */
  id:string;
  /**
   * 유저의 닉네임
   */
  nickname:string;
}

/**
 * 서버로부터 응답받은 포스트 간략정보
 */
export interface PostInterface {
  /**
   * 글 PK
   */
  id: string;
  /**
   * 글 최초 생성일
   */
  createdAt: string;
  /**
   * 유저가 설정한 닉네임
   */
  nickname: string;
  /**
   * 유저의 ID (로그인용 Email 과는 다름)
   */
  userId: string;
  /**
   * 유저 프로필 (Optional, 없을 경우 유저 닉네임 첫글자로 대체)
   */
  userImage?: string;
  /**
   * 포스트 내용
   */
  content: string;
  /**
   * 이미지 Href 배열
   */
  image: [] | string[];
}

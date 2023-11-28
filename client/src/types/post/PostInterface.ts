/**
 * 서버로부터 응답받은 포스트 간략정보
 */
export interface PostInterface {
  /**
   * 포스트 내용
   */
  postContent: string;
  /**
   * 업데이트 된 날짜
   */
  lastModifiedDate?: string;
  /**
  * 해당 게시글을 생성한유저PK
  */
  createdBy:string;
    /**
   * 수정 됬는지 여부
   */
  edited?: boolean;
  /**
   * 게시글의 PK
   */
  postNo: number;
  /**
   * 게시글 내 위치 정보
   */
  positionInfo: string;
  /**
   * 마신 술 정보
   */
  alcoholName: string;
  /**
   * 마신 술의 종류 (주종)
   */
  alcoholType: string;
  /**
   * 마신 술의 PK
   */
  alcoholNo:number;
  /**
   * 유저의 ID (로그인용 Email 과는 다름)
   */
  id: string;
  /**
   * 유저가 설정한 닉네임
   */
  nickname: string;
  /**
   * 유저가 설정한 프로필 이미지
   */
  profileImgUrls: string[];
  /**
   * 이미지 Href 배열
   */
  postAttachUrls: postAttachUrlsType[];
  /**
   * 사용자가 추가한 해시태그
   */
  tagList: string[];
  /**
   * 인용 정보
   */
  quoteInfo: QuoteInfoType[];
  /**
   * 좋아요 갯수
   */
  likeCount: number;
  /**
   * 인용횟수
   */
  quoteCount: number;
  /**
   * 댓글 갯수
   */
  commentCount : number;
  /**
   * 내가 팔로우 하는지 여부
   */
  followedByMe: boolean;
  /**
   * 내가 좋아요를 눌렀는지 여부
   */
  likedByMe: boolean;
}

type QuoteInfoType = {
  quoteNo: number;
  quoteContent: string;
};

type postAttachUrlsType = {
  attachNo:string;
  attachUrl:string;
  attachType:string
}

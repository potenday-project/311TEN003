export interface NewPostRequestInterface extends NewPostRequestAlCohol{
  /**
   * 술의 PK
   */
  postContent?: string;
  postType?: string;
  positionInfo?: string;
  tagList?: string[];
}
export interface NewPostRequestAlCohol{
  alcoholNo?: number;
  alcoholInfo?: AlcoholInfoInterface;
  alcoholFeature?: string;
}

interface AlcoholInfoInterface {
  /**
   * 술 이름
   */
  alcoholName: string;
  /**
   * DB에 지정된 Enum(?)
   */
  alcoholTypeNo: number;
  nickNames?: string[];
  manufacturer?: string;
  description?: string;
  degree?: number;
  period?: number;
  productionYear?: number;
  volume?: number;
  tagList?: string[];
}

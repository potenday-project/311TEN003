export interface NewPostRequestInterface {
  /**
   * 술의 PK
   */
  alcoholNo?: number;
  alcoholInfo?: AlcoholInfoInterface;
  alcoholFeature?: string;
  postContent?: string;
  postType?: string;
  positionInfo?: string;
  tagList?: string[];
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

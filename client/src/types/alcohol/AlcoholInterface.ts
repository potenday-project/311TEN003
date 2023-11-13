export interface AlcoholDetailInterface {
  alcoholNo: number;
  alcoholTypeNo: number;
  alcoholAttachUrls: AlcoholAttachUrlsInterface[];
  alcoholType: string;
  alcoholName: string;
  nickNames: string[];
  manufacturer: string;
  description: string;
  degree: number;
  period: number;
  productionYear: number;
  volume: number;
  tagList: string[];
}

export interface AlcoholAttachUrlsInterface {
  attachNo: number;
  attachUrl: string;
  attachType: string;
}

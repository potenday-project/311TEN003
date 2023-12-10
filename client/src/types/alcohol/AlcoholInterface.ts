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
  taste: AlcoholTasteNoteInterface;
  productionYear: number;
  volume: number;
  tagList: string[];
}

export interface AlcoholAttachUrlsInterface {
  attachNo: number;
  attachUrl: string;
  attachType: string;
}

export interface AlcoholTasteNoteInterface {
  Taste: string[];
  Aroma: string[];
  Finish: string[];
}

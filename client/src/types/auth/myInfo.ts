export interface MyInfoInterface {
  id: string;
  userNo:string;
  nickname: string;
  profileImages: ProfileImagesType[];
  introduction: string;
  followerCount: number;
}

export interface ProfileImagesType {
  attachNo: number;
  attachUrl: string;
  attachType: string;
}

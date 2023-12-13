import AttachInterface from "../attach/attachInterface";

interface FollowingUserInterface {
  nickname: string;
  id: string;
  userNo: number;
  introduction: string;
  createdBy: number;
  profileImgUrls: AttachInterface[];
}

export default FollowingUserInterface;

import { ProfileImagesType } from "../user/userInfoInterface";

interface PostCommentListInterface {
  list: PostCommentInterface[];
  totalCount: number;
}

export interface PostCommentInterface {
  commentNo: number;
  commentContent: string;
  createdDate: string;
  lastModifiedDate: string;
  createdBy: number;
  userId: string;
  nickname: string;
  profileImgUrls: ProfileImagesType[];
}

export default PostCommentListInterface;

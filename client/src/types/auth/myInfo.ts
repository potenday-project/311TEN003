import { UserInfoInterface } from "../user/userInfoInterface";

export interface MyInfoInterface
  extends Omit<UserInfoInterface, "isFollowing"> {}

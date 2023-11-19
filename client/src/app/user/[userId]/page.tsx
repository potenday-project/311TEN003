import UserInfoCard from "@/components/user/info/UserInfoCard";
import PostCardList from "@/components/post/PostCardList";
import { getUserInfoFn } from "@/queries/user/useUserInfoQuery";
import getTokenFromCookies from "@/utils/getTokenFromCookies";

const page = async ({ params }: { params: { userId: string } }) => {
  const token = await getTokenFromCookies();
  const userData = await getUserInfoFn(params.userId, {
    headers: { Authorization: token },
  });
  return (
    <>
      <UserInfoCard initialData={userData} userId={params.userId} />
      <PostCardList searchUserNos={params.userId} />
    </>
  );
};

export default page;

import UserInfoCard from "@/components/user/info/UserInfoCard";
import axios from "@/libs/axios";
import { MyInfoInterface } from "@/types/auth/myInfo";

import NoResult from "@/assets/images/noResult.png";
import { Box } from "@mui/material";
import Image from "next/image";
import PostCardList from "@/components/post/PostCardList";

const page = async ({ params }: { params: { userId: string } }) => {
  try {
    const { data } = await axios.get<
      MyInfoInterface & { isFollowing: boolean }
    >(`/user/${params.userId}/summary`);
    return (
      <>
        <UserInfoCard data={data} />
        <PostCardList/>
      </>
    );
  } catch {
    return (
      <Box
        sx={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          py: 8,
        }}
      >
        <Image priority src={NoResult} alt="no result alert" />
      </Box>
    );
  }
};

export default page;

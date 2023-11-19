"use client";

import NoResult from "@/assets/images/noResult.png";
import HOME from "@/const/clientPath";
import { Box, Button } from "@mui/material";
import Image from "next/image";
import { useRouter } from "next/navigation";

const userInfoError = (props: {
  error: Error & { digest?: string };
  reset: () => void;
}) => {
  const router = useRouter();
  return (
    <Box sx={CenteringWRapper}>
      <Image priority src={NoResult} alt="no result alert" />
      <Button onClick={() => router.push(HOME)}>홈으로 가기</Button>
    </Box>
  );
};

const CenteringWRapper = {
  display: "flex",
  alignItems: "center",
  justifyContent: "center",
  flexDirection: "column",
  gap: 4,
  py: 8,
};

export default userInfoError;

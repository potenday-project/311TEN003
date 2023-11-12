import { Paper } from "@mui/material";

import DevelopingNoticeImgae from "@/assets/images/developing.png";
import Image from "next/image";

const DevelopingPage = () => {
  return (
    <Paper
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "calc(100vh - 56px)",
      }}
    >
      <Image priority src={DevelopingNoticeImgae} alt="개발중 알림" />
    </Paper>
  );
};

export default DevelopingPage;

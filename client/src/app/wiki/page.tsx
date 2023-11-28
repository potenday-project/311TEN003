import AlcoholPagination from "@/components/wiki/AlcoholPagination";
import WikiAlcoholSelector from "@/components/wiki/WikiAlcoholSelector";
import { Stack } from "@mui/material";
import SectionHeading from "@/components/SectionHeading";

import DevelopingNoticeImgae from "@/assets/images/developing.png";
import Image from "next/image";

const WikiPage = async () => {
  return (
    <>
      <SectionHeading
        title={"투파이아 게시판"}
        subTitle={"투파이아들이 쓴 리뷰를 확인할 수 있어요!"}
      />
      <WikiAlcoholSelector />
      <AlcoholPagination />

      <SectionHeading title={"술 정보"} subTitle={"곧 출시 됩니다!"} />
      <Stack alignItems="center">
        <Image src={DevelopingNoticeImgae} alt="개발중" />
      </Stack>
    </>
  );
};

export default WikiPage;

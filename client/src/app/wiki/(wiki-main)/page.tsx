import AlcoholPagination from "@/components/wiki/AlcoholPagination";
import WikiAlcoholSelector from "@/components/wiki/WikiAlcoholSelector";

import SectionHeading from "@/components/SectionHeading";


const WikiPage = () => {
  return (
    <>
      <SectionHeading title={"술 정보"} subTitle={"다양한 술 정보를 알아보세요!"} />
      <WikiAlcoholSelector />
      <AlcoholPagination />
    </>
  );
};

export default WikiPage;

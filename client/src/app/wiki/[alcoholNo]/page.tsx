import { HTML_TITLE, nameOfApp } from "@/const/brand";
import { getAlcoholDetailById } from "@/queries/alcohol/useGetAlcoholDetailQuery";
import { Metadata } from "next";
import AlcoholDetailPage from "./(components)/AlcoholDetailPage";
import CustomAppbar from "@/components/layout/CustomAppbar";
import CustomContainer from "@/components/layout/CustomContainer";
import PostCardList from "@/components/post/PostCardList";

type Props = {
  params: { alcoholNo: string };
};

export async function generateMetadata({ params }: Props): Promise<Metadata> {
  const { alcoholName, alcoholType, taste } = await getAlcoholDetailById(
    params.alcoholNo
  );

  return {
    metadataBase: new URL("https://tupaia.me"),
    title: HTML_TITLE(`${alcoholName} 정보`),
    description: `'${nameOfApp}'에서 ${alcoholType} ${alcoholName} 의 정보를 알아보세요`,
    keywords: [
      alcoholName,
      `${alcoholName} 맛`,
      `${alcoholName} 후기`,
      `${alcoholName} 리뷰`,
      `${alcoholName} 향`,
      `${alcoholName} 테이스트 노트`,
      nameOfApp,
      alcoholType,
      `${alcoholType}추천`,
      taste.Aroma.map((aroma) => `${aroma}향 ${alcoholType}`).join(", "),
      taste.Finish.map((finish) => `${finish}피니시 ${alcoholType}`).join(", "),
      taste.Taste.map((taste) => `${taste}맛 ${alcoholType}`).join(", "),
    ],
    openGraph: {
      title: `${nameOfApp} | ${alcoholType} ${alcoholName}`,
      description: `'${nameOfApp}'에서 ${alcoholType} ${alcoholName} 의 정보를 알아보세요`,
    },
  };
}

const page = async ({ params }: Props) => {
  const initialData = await getAlcoholDetailById(params.alcoholNo);
  const searchKeyword = initialData.alcoholName;

  return (
    <>
      <CustomAppbar title={initialData.alcoholName} />
      <CustomContainer>
        <AlcoholDetailPage
          alcoholNo={params.alcoholNo}
          initialData={initialData}
        >
          {/* 포스트리스트자리 */}
          {/* FIXME */}
          {/* <PostCardList searchKeyword={searchKeyword}/> */}
        </AlcoholDetailPage>
      </CustomContainer>
    </>
  );
};

export default page;

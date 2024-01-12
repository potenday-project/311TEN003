import { HTML_TITLE, nameOfApp } from "@/const/brand";
import { getAlcoholDetailById } from "@/queries/alcohol/useGetAlcoholDetailQuery";
import { Metadata } from "next";
import AlcoholDetailPage from "../../../components/wiki/detail/AlcoholDetailPage";
import CustomAppbar from "@/components/layout/CustomAppbar";
import CustomContainer from "@/components/layout/CustomContainer";
import AlcoholDetailPostCardList from "@/components/wiki/detail/AlcoholDetailPostCardList";
import { getPostListQueryFn } from "@/queries/post/useGetPostListInfiniteQuery";

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
      taste.Aroma?.map((aroma) => `${aroma}향 ${alcoholType}`).join(", "),
      taste.Finish?.map((finish) => `${finish}피니시 ${alcoholType}`).join(", "),
      taste.Taste?.map((taste) => `${taste}맛 ${alcoholType}`).join(", "),
    ],
    openGraph: {
      title: `${nameOfApp} | ${alcoholType} ${alcoholName}`,
      description: `'${nameOfApp}'에서 ${alcoholType} ${alcoholName} 의 정보를 알아보세요`,
    },
  };
}

const page = async ({ params }: Props) => {
  const initialData = await getAlcoholDetailById(params.alcoholNo);
  const initialPostData = await getPostListQueryFn({
    searchAlcoholNos: Number(params?.alcoholNo),
    size: 3,
  });
  const searchKeyword = initialData.alcoholName;

  return (
    <>
      <CustomAppbar title={searchKeyword} />
      <CustomContainer>
        <AlcoholDetailPage
          alcoholNo={params.alcoholNo}
          initialData={initialData}
        >
          <AlcoholDetailPostCardList
            initialData={initialPostData}
            alcoholNo={Number(params.alcoholNo)}
          />
        </AlcoholDetailPage>
      </CustomContainer>
    </>
  );
};

export default page;

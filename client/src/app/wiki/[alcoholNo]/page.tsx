import { HTML_TITLE } from "@/const/brand";
import { getAlcoholDetailById } from "@/queries/alcohol/useGetAlcoholDetailQuery";
import { Metadata } from "next";
import AlcoholDetailPage from "./(components)/AlcoholDetailPage";
import CustomAppbar from "@/components/layout/CustomAppbar";
import CustomContainer from "@/components/layout/CustomContainer";

type Props = {
  params: { alcoholNo: string };
};

export async function generateMetadata({ params }: Props): Promise<Metadata> {
  const { alcoholName } = await getAlcoholDetailById(params.alcoholNo);

  return {
    title: HTML_TITLE(`${alcoholName} 정보`),
  };
}

const page = async ({ params }: Props) => {
  const initialData = await getAlcoholDetailById(params.alcoholNo);

  return (
    <>
      <CustomAppbar />
      <CustomContainer>
        <AlcoholDetailPage
          alcoholNo={params.alcoholNo}
          initialData={initialData}
        >
          {JSON.stringify(initialData)}
        </AlcoholDetailPage>
      </CustomContainer>
    </>
  );
};

export default page;

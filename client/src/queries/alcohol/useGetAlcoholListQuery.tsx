import { GET_ALCOHOL_LIST } from "@/const/serverPath";
import axios from "@/libs/axios";
import PageNated from "@/types/Pagenated";
import { AlcoholDetailInterface } from "@/types/alcohol/AlcoholInterface";
import { useQuery } from "@tanstack/react-query";

export interface useGetAlcoholListQueryInterface {
  page?: number;
  size?: number;
  searchKeyword?: string;
  alcoholType?: number;
}

const useGetAlcoholListQuery = ({
  searchKeyword = "",
  size = 5,
  page = 0,
  alcoholType,
}: useGetAlcoholListQueryInterface) => {
  return useQuery({
    queryKey: AlcohilListQueryKey.byKeyword({
      page,
      size,
      searchKeyword,
      alcoholType,
    }),
    queryFn: async () =>
      await getAlcoholListByKeyword({ searchKeyword, size, page, alcoholType }),
    enabled: searchKeyword != undefined,
  });
};

export const getAlcoholListByKeyword = async ({
  searchKeyword,
  size,
  page,
  alcoholType,
}: useGetAlcoholListQueryInterface) => {
  const { data } = await axios.get<PageNated<AlcoholDetailInterface>>(
    GET_ALCOHOL_LIST,
    {
      params: {
        page,
        size,
        searchKeyword,
        alcoholType,
      },
    }
  );
  return data;
};

export const AlcohilListQueryKey = {
  all: ["alcohol"] as const,
  byKeyword: ({
    searchKeyword,
    size,
    page,
    alcoholType,
  }: useGetAlcoholListQueryInterface) =>
    ["alcohol", { searchKeyword, size, page, alcoholType }] as const,
};

export default useGetAlcoholListQuery;

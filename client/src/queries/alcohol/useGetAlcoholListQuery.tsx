import { GET_ALCOHOL_LIST } from "@/const/serverPath";
import axios from "@/libs/axios";
import { AlcoholDetailInterface } from "@/types/alcohol/AlcoholInterface";
import { useQuery } from "@tanstack/react-query";

export interface useGetAlcoholListQueryInterface {
  page?: number;
  size?: number;
  searchKeyword?: string;
}

const useGetAlcoholListQuery = ({
  searchKeyword = "",
  size = 5,
  page = 0,
}: useGetAlcoholListQueryInterface) => {
  return useQuery({
    queryKey: AlcohilListQueryKey.byKeyword({ page, size, searchKeyword }),
    queryFn: async () =>
      await getAlcoholListByKeyword({ searchKeyword, size, page }),
    enabled: searchKeyword != undefined,
  });
};

export const getAlcoholListByKeyword = async ({
  searchKeyword,
  size,
  page,
}: useGetAlcoholListQueryInterface) => {
  const { data } = await axios.get<{
    list: AlcoholDetailInterface[];
    totalCount: number;
  }>(GET_ALCOHOL_LIST, {
    params: {
      page,
      size,
      searchKeyword,
    },
  });
  return data;
};

export const AlcohilListQueryKey = {
  all: ["alcohol"] as const,
  byKeyword: ({ searchKeyword, size, page }: useGetAlcoholListQueryInterface) =>
    ["alcohol", { searchKeyword, size, page }] as const,
};

export default useGetAlcoholListQuery;

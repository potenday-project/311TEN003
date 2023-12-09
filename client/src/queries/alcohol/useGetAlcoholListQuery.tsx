import { GET_ALCOHOL_LIST } from "@/const/serverPath";
import axios from "@/libs/axios";
import { AlcoholDetailInterface } from "@/types/alcohol/AlcoholInterface";
import { useQuery } from "@tanstack/react-query";

const useGetAlcoholListQuery = (keyword?: string) => {
  return useQuery({
    queryKey: AlcohilListQueryKey.byKeyword(keyword),
    queryFn: async () => await getAlcoholListByKeyword(keyword),
    enabled: keyword!=undefined,
  });
};

export const getAlcoholListByKeyword = async (keyword?: string) => {
  const { data } = await axios.get<{
    list: AlcoholDetailInterface[];
    totalCount: number;
  }>(GET_ALCOHOL_LIST, {
    params: {
      page: 0,
      size: 5,
      searchKeyword: keyword,
    },
  }); 
  return data;
};

export const AlcohilListQueryKey = {
  all: ["alcohol"] as const,
  byKeyword: (keyword?: string) => ["alcohol", { keyword }] as const,
};

export default useGetAlcoholListQuery;

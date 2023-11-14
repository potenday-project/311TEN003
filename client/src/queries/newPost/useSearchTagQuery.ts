import axios from "@/libs/axios";
import { useQuery } from "@tanstack/react-query";

const useSearchTagQuery = (keyword?: string) => {
  return useQuery({
    queryKey: useSearchTagListKey.byKeyword(keyword),
    queryFn: async() => await getTagBySearchName(keyword),
  });
};

export interface TagSearchResult {
  name: string;
  createdDate: string;
}

export const getTagBySearchName = async (searchKeyword?: string) => {
  const { data } = await axios.get<{
    list: TagSearchResult[];
    totalCount: number;
  }>("/tag", {
    params: { searchKeyword },
  });
  return data;
};

const useSearchTagListKey = {
  all: ["tag"] as const,
  byKeyword: (keyword?: string) => ["tags", keyword] as const,
};

export default useSearchTagQuery;

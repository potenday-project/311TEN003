import { GET_ALCOHOL_DETAIL } from "@/const/serverPath";
import axios from "@/libs/axios";
import { AlcoholDetailInterface } from "@/types/alcohol/AlcoholInterface";
import { useSuspenseQuery } from "@tanstack/react-query";

const useGetAlcoholDetailQuery = (
  id: string,
  initialData?: AlcoholDetailInterface
) =>
  useSuspenseQuery({
    queryKey: AlcoholDetailQueryKey.byId(id),
    queryFn: () => getAlcoholDetailById(id),
    initialData,
  });

export const AlcoholDetailQueryKey = {
  all: ["alcoholDetail"] as const,
  byId: (id: string) => ["alcoholDetail", id] as const,
};

export const getAlcoholDetailById = async (id: string) => {
  const { data } = await axios.get<AlcoholDetailInterface>(
    GET_ALCOHOL_DETAIL(id)
  );
  return data;
};

export default useGetAlcoholDetailQuery;

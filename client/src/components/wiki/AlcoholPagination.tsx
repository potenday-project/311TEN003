"use client";
import useGetAlcoholListQuery, {
  AlcohilListQueryKey,
  getAlcoholListByKeyword,
} from "@/queries/alcohol/useGetAlcoholListQuery";
import AlcoholList from "@/components/wiki/AlcoholList";
import { Pagination, Stack } from "@mui/material";
import usePushToWikiDetail from "@/hooks/wiki/usePushToWikiDetail";
import { useEffect, useState } from "react";
import { useQueryClient } from "@tanstack/react-query";

const AlcoholPagenation = () => {
  const [currentPage, setCurrentPage] = useState(1);
  const size = 10;

  const { data: alcohols } = useGetAlcoholListQuery({
    searchKeyword: "",
    page: currentPage - 1,
    size,
  });

  const [totalCount, setTotalCount] = useState(alcohols?.totalCount);

  useEffect(() => {
    const isSameWithPreviousValue =
      totalCount === alcohols?.totalCount || alcohols?.totalCount === undefined;

    if (isSameWithPreviousValue) return;
    setTotalCount(alcohols?.totalCount);
  }, [alcohols]);

  const queryClient = useQueryClient();

  useEffect(() => {
    const handler = async () => {
      const nextPageParams = {
        searchKeyword: "",
        size,
        page: currentPage + 1,
      };

      const fetchedNextPage = queryClient.getQueryData(
        AlcohilListQueryKey.byKeyword(nextPageParams)
      );

      if (fetchedNextPage) return;
      const nextPage = await getAlcoholListByKeyword(nextPageParams);

      queryClient.setQueryData(
        AlcohilListQueryKey.byKeyword(nextPageParams),
        nextPage
      );
    };

    handler();
  }, [currentPage]);

  const onClickElementHandler = usePushToWikiDetail();

  return (
    <Stack alignItems="center" gap={2}>
      <Stack gap={1} alignItems="center" width={"100%"}>
        <AlcoholList
          data={alcohols?.list}
          size={size}
          onClickElement={({ alcoholName, alcoholNo }) =>
            onClickElementHandler({ alcoholName, alcoholNo })
          }
        />
      </Stack>
      <Pagination
        count={(totalCount ?? 0) / size - 1}
        page={currentPage}
        siblingCount={2}
        onChange={(_e, page) => setCurrentPage(page)}
      />
    </Stack>
  );
};

export default AlcoholPagenation;

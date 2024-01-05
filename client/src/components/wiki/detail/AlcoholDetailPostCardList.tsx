"use client";

import AlcoholeDetailPostCard from "./AlcoholeDetailPostCard";
import ArrowDownwardIcon from "@mui/icons-material/ArrowDownward";
import { Stack, Box, Button, CircularProgress, Typography } from "@mui/material";

import AlcoholDetailPostCardListHeader from "./AlcoholDetailPostCardListHeader";
import useGetPostListInfiniteQuery, {
  AugmentedGetPostListResponse,
} from "@/queries/post/useGetPostListInfiniteQuery";
import { SEARCH_BY_ALCOHOLNO } from "@/const/clientPath";

type Props = {
  initialData?: AugmentedGetPostListResponse;
  alcoholNo: number;
};

const AlcoholDetailPostCardList = ({ initialData, alcoholNo }: Props) => {
  const { data, hasNextPage, isFetching, fetchNextPage } =
    useGetPostListInfiniteQuery({
      initialData,
      searchAlcoholNos: alcoholNo,
      size: 3,
    });
  const hasPost = (data?.pages?.[0]?.content?.length ?? 0) > 0;

  return (
    <div>
      <AlcoholDetailPostCardListHeader
        totalContents={data?.pages?.[0]?.totalElements ?? 0}
        href={SEARCH_BY_ALCOHOLNO(alcoholNo)}
      />
      <Stack gap={2} py={2} mt={6}>
        {hasPost ? (
          <>
            {data?.pages.map(({ content }) =>
              content.map((data) => (
                <AlcoholeDetailPostCard {...data} key={data.postNo} />
              ))
            )}
            {hasNextPage && (
              <Button
                sx={ButtonStyle}
                onClick={() => fetchNextPage()}
                disabled={isFetching}
              >
                캐스크 더보기
                <ArrowDownwardIcon sx={{ color: "primary.main" }} />
              </Button>
            )}
            {isFetching && <CircularProgress sx={{ mx: "auto" }} />}
          </>
        ) : (
          <Typography textAlign='center'>작성된 캐스크가 없습니다</Typography>
        )}
      </Stack>
      <Box
        bgcolor={"gray.primary"}
        position={"absolute"}
        height={16}
        left={0}
        right={0}
      />
    </div>
  );
};

const ButtonStyle = {
  backgroundColor: "#F6EAFB",
  color: "primary.main",
  ":hover": {
    backgroundColor: "#F6EAFB",
  },
};

export default AlcoholDetailPostCardList;

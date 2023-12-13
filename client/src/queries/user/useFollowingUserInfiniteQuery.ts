"use client";
import { FOLLOWING_USER } from "@/const/serverPath";
import useAxiosPrivate from "@/hooks/useAxiosPrivate";
import { useSuspenseInfiniteQuery } from "@tanstack/react-query";
import Pagenated, { PagenationParams } from "@/types/Pagenated";
import FollowingUserInterface from "@/types/user/followingUserInterface";

const useFollowingUserInfiniteQuery = () => {
  return useSuspenseInfiniteQuery({
    queryKey: followingUserQueryKey.all,

    queryFn: async ({ pageParam = 0 }) =>
      await getFollowingUserFn({ page: pageParam }),

    getNextPageParam: ({ currentPage, hasNextPage }) =>
      hasNextPage ? currentPage + 1 : undefined,

    getPreviousPageParam: ({ currentPage }) =>
      currentPage > 0 ? currentPage - 1 : undefined,
    initialPageParam: 0,
  });
};

export const getFollowingUserFn = async ({
  page = 0,
  size = 10,
  sort = "desc",
}: PagenationParams) => {
  const axiosPrivate = useAxiosPrivate();
  const { data } = await axiosPrivate.get<Pagenated<FollowingUserInterface>>(
    FOLLOWING_USER,
    {
      params: {
        page,
        size,
        sort,
      },
    }
  );
  return {
    ...data,
    currentPage: page,
    hasNextPage: data.totalElements / ((page + 1) * size) > 1,
  };
};

export const followingUserQueryKey = {
  all: ["followingUser"],
};

export default useFollowingUserInfiniteQuery;

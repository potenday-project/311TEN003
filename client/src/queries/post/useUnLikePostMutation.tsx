"use client";
import axios from "@/libs/axios";
import { PostInterface } from "@/types/post/PostInterface";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { getPostListInfiniteQueryKey } from "./useGetPostListInfiniteQuery";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import { POST_UN_LIKE_URL } from "@/const/serverPath";
/**
 * 좋아요를 취소하고, 게시글을 invalidation 하는 쿼리
 * @returns
 */
const useLikePostMutation = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (id: PostInterface["postNo"]) => useLikePostMutationFn(id),
    onSuccess: () =>
      queryClient.invalidateQueries({
        queryKey: getPostListInfiniteQueryKey.all,
      }),
  });
};

/**
 * PostNo 를 기반으로 좋아요를 취소하는 함수
 * @param id PostNo (게시글 PK)
 * @returns
 */
export const useLikePostMutationFn = async (id: PostInterface["postNo"]) => {
  const token = getTokenFromLocalStorage();
  const { data } = await axios.post(POST_UN_LIKE_URL(String(id)), null, {
    headers: { Authorization: token },
  });
  return data;
};

export default useLikePostMutation;

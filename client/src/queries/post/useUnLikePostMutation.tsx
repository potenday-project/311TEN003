"use client";
import axios from "@/libs/axios";
import { PostInterface } from "@/types/post/PostInterface";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { getPostListInfiniteQueryKey } from "./useGetPostListInfiniteQuery";
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
  const token = localStorage.getItem("accessToken");
  // FIXME 리터럴제거
  const { data } = await axios.post(`/posts/like-cancel/${id}`, null, {
    headers: { Authorization: token },
  });
  return data;
};

export default useLikePostMutation;

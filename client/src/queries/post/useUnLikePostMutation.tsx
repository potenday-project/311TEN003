"use client";
import axios from "@/libs/axios";
import { PostInterface } from "@/types/post/PostInterface";
import {
  InfiniteData,
  useMutation,
  useQueryClient,
} from "@tanstack/react-query";
import {
  AugmentedGetPostListResponse,
  getPostListInfiniteQueryKey,
} from "./useGetPostListInfiniteQuery";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import { POST_UN_LIKE_URL } from "@/const/serverPath";
import { useErrorHandler } from "@/utils/errorHandler";
import { PostcardContextInterface } from "@/store/PostCardContext";
import { useOptimisticUpdatePostList } from "@/queries/post/updator/useOptimisticUpdatePostList";
import { useOptimisticUpdatePostDetail } from "./updator/useOptimisticUpdatePostDetail";
import { postDetailQueryKey } from "./useGetPostDetailQuery";

/**
 * 좋아요를 취소하고, 게시글을 invalidation 하는 쿼리
 * @param context 현재 보고있는 페이지의 컨텍스트 (검색,유저페이지,메인페이지의 글 리스트가 같은 쿼리를 바라보고있으므로,
 * 적절한 Invalidation을 위한 쿼리키를 추출하기 위해 사용됨)
 * @returns
 */
const useLikePostMutation = (context?: PostcardContextInterface) => {
  const queryClient = useQueryClient();
  const errorHandler = useErrorHandler();

  const postListUpdator = useOptimisticUpdatePostList({ type: "unlike" });
  const postDetailUpdator = useOptimisticUpdatePostDetail({ type: "unlike" });

  const listQueryKey = getPostListInfiniteQueryKey.byKeyword({
    keyword: context?.searchKeyword,
    userNo: context?.searchUserNos,
  });
  return useMutation({
    mutationFn: (id: PostInterface["postNo"]) => useLikePostMutationFn(id),
    onMutate: (id) => {
      // 진행중인 쿼리를 캔슬
      // [리스트 쿼리]
      queryClient.cancelQueries({ queryKey: listQueryKey });
      // [디테일페이지 쿼리]
      queryClient.cancelQueries({
        queryKey: postDetailQueryKey.byId(String(id)),
      });

      // 쿼리 스냅샷 생성
      // [리스트 쿼리]
      const listQuerySnapshot =
        queryClient.getQueryData<InfiniteData<AugmentedGetPostListResponse>>(
          listQueryKey
        );
      // [디테일쿼리]
      const detailQuerySnapshot = queryClient.getQueryData(
        postDetailQueryKey.byId(String(id))
      );

      // Optimastic Update
      // [리스트 쿼리]
      postListUpdator(listQueryKey, String(id));
      // [디테일 쿼리]
      postDetailUpdator(String(id));

      // onError에서 사용할 쿼리스냅샷 리턴
      return {
        querySnapshot: { list: listQuerySnapshot, detail: detailQuerySnapshot },
      };
    },
    onSuccess: () =>
      queryClient.invalidateQueries({
        queryKey: getPostListInfiniteQueryKey.all,
      }),
    onError: (err, queryFnParams, context) => {
      errorHandler(err);
      if (!context) {
        return;
      }
      // [리스트 쿼리]
      queryClient.setQueryData(listQueryKey, context?.querySnapshot.list);
      // [디테일 쿼리]
      queryClient.setQueryData(
        postDetailQueryKey.byId(String(queryFnParams)),
        context?.querySnapshot.detail
      );
    },
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
function postDetailUpdator(arg0: string) {
  throw new Error("Function not implemented.");
}

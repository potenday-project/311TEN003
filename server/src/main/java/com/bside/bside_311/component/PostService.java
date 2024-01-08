package com.bside.bside_311.component;

import com.bside.bside_311.dto.GetPostsToOneMvo;
import com.bside.bside_311.dto.PostSearchCondition;
import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.PostMybatisRepository;
import com.bside.bside_311.repository.PostRepository;
import com.bside.bside_311.util.AuthUtil;
import com.bside.bside_311.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;
  private final PostMybatisRepository postMybatisRepository;

  public void savePost(Post post) {
    postRepository.save(post);
  }

  public Post findPost(Long postNo) {
    return postRepository.findByIdAndDelYnIs(postNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException(MessageUtil.POST_NOT_FOUND_MSG));
  }

  public void deletePost(Post post) {
    post.setDelYn(YesOrNo.Y);
    postRepository.save(post);
  }

  public Page<Post> getPostListCommon(Pageable pageable, String searchKeyword,
                                      List<Long> searchUserNoList,
                                      Boolean isLikedByMe, Boolean isCommentedByMe,
                                      List<Long> searchAlcoholNoList) {
    return postRepository.searchPageSimple(
        PostSearchCondition.builder().searchKeyword(searchKeyword)
                           .searchUserNoList(searchUserNoList)
                           .isLikedByMe(isLikedByMe)
                           .isCommentedByMe(isCommentedByMe)
                           .myUserNo(
                               AuthUtil.getUserNoFromAuthentication())
                           .searchAlcoholNoList(searchAlcoholNoList)
                           .build(), pageable);
  }

  public Page<Post> getPostListPopular(Long page, Long size) {
    return postRepository.searchPagePopular(page, size);
  }

  public Map<Long, GetPostsToOneMvo> getGetPostsToOneMvoMap(List<Long> postNos) {
    List<GetPostsToOneMvo> postsToOneList =
        CollectionUtils.isNotEmpty(postNos) ? postMybatisRepository.getPostsToOne(postNos) :
            List.of();
    Map<Long, GetPostsToOneMvo> postsToOneMap = new HashMap<>();
    for (GetPostsToOneMvo getPostsToOneMvo : postsToOneList) {
      postsToOneMap.put(getPostsToOneMvo.getPostNo(), getPostsToOneMvo);
    }
    return postsToOneMap;
  }


}

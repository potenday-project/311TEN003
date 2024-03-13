package com.bside.bside_311.component;

import com.bside.bside_311.dto.GetPostResponseDto;
import com.bside.bside_311.dto.GetPostVo;
import com.bside.bside_311.dto.GetPostsMvo;
import com.bside.bside_311.dto.GetPostsToOneMvo;
import com.bside.bside_311.dto.GetQuotesByPostResponseDto;
import com.bside.bside_311.dto.PostResponseDto;
import com.bside.bside_311.dto.PostSearchCondition;
import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.Comment;
import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.PostLike;
import com.bside.bside_311.entity.PostQuote;
import com.bside.bside_311.entity.PostTag;
import com.bside.bside_311.entity.Tag;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.model.AbstractUserAuthInfo;
import com.bside.bside_311.repository.CommentRepository;
import com.bside.bside_311.repository.PostLikeRepository;
import com.bside.bside_311.repository.PostMybatisRepository;
import com.bside.bside_311.repository.PostQuoteRepository;
import com.bside.bside_311.repository.PostRepository;
import com.bside.bside_311.repository.TagRepository;
import com.bside.bside_311.repository.UserFollowRepository;
import com.bside.bside_311.service.TagService;
import com.bside.bside_311.util.AuthUtil;
import com.bside.bside_311.util.MessageUtil;
import com.bside.bside_311.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PostService {
  private final TagService tagService;
  private final TagRepository tagRepository;
  private final PostRepository postRepository;
  private final PostMybatisRepository postMybatisRepository;
  private final PostLikeRepository postLikeRepository;
  private final UserFollowRepository userFollowRepository;
  private final PostQuoteRepository postQuoteRepository;
  private final CommentRepository commentRepository;

  public void savePost(Post post) {
    postRepository.save(post);
  }
  public Post findPost(Long postNo) {
    return findPost(postNo, MessageUtil.POST_NOT_FOUND_MSG);
  }

  public Post findPost(Long postNo, String notFoundMsg) {
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


  public void putTagsToPost(Post post, List<String> tagStrList) {
    List<Tag> tags = tagService.addOrSetTags(tagStrList);
    post.removeAllPostTagsAndAddNewPostTags(tags);
  }

  public void linkAlcoholToPost(Post post, Alcohol alcohol, String alcoholFeature) {
    post.removeAllPostAlcoholsAndAddNewPostAlcohols(List.of(alcohol), alcoholFeature);
  }

  public boolean isThisPostLikedByMe(Post post, Long myUserNo) {
    if (ObjectUtils.isEmpty(post) || ObjectUtils.isEmpty(post.getId()) ||
            ObjectUtils.isEmpty(myUserNo)) {
      return false;
    }
    return postLikeRepository.findByUserAndPostAndDelYnIs(User.of(myUserNo), post, YesOrNo.N)
                             .isPresent();
  }

  public boolean getFollowedByMe(Post post, Long myUserNo) {
    if (ObjectUtils.isEmpty(post) || ObjectUtils.isEmpty(post.getCreatedBy()) || ObjectUtils
        .isEmpty(
            myUserNo)) {
      return false;
    }
    return userFollowRepository.findByFollowingAndFollowedAndDelYnIs(
        User.of(myUserNo), User.of(post.getCreatedBy()), YesOrNo.N).isPresent();
  }

  public Long getQuoteCount(Post post) {
    return postQuoteRepository.countByPostAndDelYnIs(post, YesOrNo.N);
  }

  public Long getLikeCount(Post post) {
    return postLikeRepository.countByPostAndDelYnIs(post, YesOrNo.N);
  }

  public Comment addCommentToPost(Post post, Comment comment) {
    post.addComment(comment);
    commentRepository.save(comment);
    return comment;
  }

  public PostQuote addQuoteToPost(Post post, Post quotedPost) {
    PostQuote postQuote =
        postQuoteRepository.findByPostAndQuoteAndDelYnIs(post, quotedPost, YesOrNo.N)
                           .orElse(PostQuote.of(post, quotedPost));
    postQuoteRepository.save(postQuote);
    return postQuote;
  }

  public void deleteQuote(Long quoteNo, AbstractUserAuthInfo accessUserAuthInfo) {
    PostQuote postQuote = postQuoteRepository.findByIdAndDelYnIs(quoteNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException("인용이 존재하지 않습니다."));
    ValidateUtil.resourceChangeableCheckByThisUserAuthInfo(postQuote, accessUserAuthInfo);
    postQuote.setDelYn(YesOrNo.Y);
  }

  public GetQuotesByPostResponseDto getQuoteByPost(Post post) {
    List<PostQuote> postQuotes = postQuoteRepository.findByPostAndDelYnIs(post, YesOrNo.N);
    return GetQuotesByPostResponseDto.of(postQuotes);
  }

  public void userLikePost(Post post, User user) {
    PostLike postLike =
        postLikeRepository.findByUserAndPostAndDelYnIs(user, post, YesOrNo.N).orElse(
            PostLike.of(user, post));
    postLikeRepository.save(postLike);
  }

  public List<Comment> getComments(Post post) {
    return commentRepository.findByPostAndDelYnIs(post, YesOrNo.N);
  }

  public Comment getCommentById(long id) {
    return commentRepository.findByIdAndDelYnIs(id, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
  }

  public void userCancelLikePost(Post post, User user, AbstractUserAuthInfo accessUserAuthInfo) {
    PostLike postLike =
        postLikeRepository.findByUserAndPostAndDelYnIs(user, post, YesOrNo.N).orElseThrow(
            () -> new IllegalArgumentException("좋아요가 존재하지 않습니다."));
    ValidateUtil.resourceChangeableCheckByThisUserAuthInfo(postLike, accessUserAuthInfo);
    postLike.setDelYn(YesOrNo.Y);
  }
}

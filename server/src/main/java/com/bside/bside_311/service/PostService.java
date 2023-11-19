package com.bside.bside_311.service;

import com.bside.bside_311.dto.AddCommentRequestDto;
import com.bside.bside_311.dto.AddCommentResponseDto;
import com.bside.bside_311.dto.AddPostResponseDto;
import com.bside.bside_311.dto.AttachDto;
import com.bside.bside_311.dto.EditCommentRequestDto;
import com.bside.bside_311.dto.EditPostRequestDto;
import com.bside.bside_311.dto.GetPostCommentsResponseDto;
import com.bside.bside_311.dto.GetPostResponseDto;
import com.bside.bside_311.dto.GetPostVo;
import com.bside.bside_311.dto.GetPostsMvo;
import com.bside.bside_311.dto.GetQuotesByPostResponseDto;
import com.bside.bside_311.dto.PostResponseDto;
import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.AttachType;
import com.bside.bside_311.entity.Comment;
import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.PostAlcohol;
import com.bside.bside_311.entity.PostLike;
import com.bside.bside_311.entity.PostQuote;
import com.bside.bside_311.entity.PostTag;
import com.bside.bside_311.entity.Tag;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.AlcoholRepository;
import com.bside.bside_311.repository.AttachRepository;
import com.bside.bside_311.repository.CommentRepository;
import com.bside.bside_311.repository.PostLikeRepository;
import com.bside.bside_311.repository.PostMybatisRepository;
import com.bside.bside_311.repository.PostQuoteRepository;
import com.bside.bside_311.repository.PostRepository;
import com.bside.bside_311.repository.TagRepository;
import com.bside.bside_311.repository.UserFollowRepository;
import com.bside.bside_311.repository.UserRepository;
import com.bside.bside_311.util.AuthUtil;
import com.bside.bside_311.util.MessageUtil;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {
  private final TagService tagService;
  private final UserRepository userRepository;
  private final PostLikeRepository postLikeRepository;
  private final PostRepository postRepository;
  private final PostMybatisRepository postMybatisRepository;
  private final TagRepository tagRepository;
  private final AttachRepository attachRepository;
  private final AlcoholRepository alcoholRepository;

  private final CommentRepository commentRepository;
  private final PostQuoteRepository postQuoteRepository;
  private final UserFollowRepository userFollowRepository;

  public AddPostResponseDto addPost(
      Post post, Long alcoholNo, String alcoholFeature,
      List<String> tagStrList) {
    log.info(">>> PostService.addPost");

    postRepository.save(post);
    if (CollectionUtils.isNotEmpty(tagStrList)) {
      List<Tag> tags = tagService.addOrSetTags(tagStrList);
      post.removeAllPostTagsAndAddNewPostTags(tags);
    }

    postRepository.save(post);

    if (alcoholNo != null) {
      Alcohol alcohol = alcoholRepository.findByIdAndDelYnIs(alcoholNo, YesOrNo.N).orElseThrow(
          () -> new IllegalArgumentException("술이 존재하지 않습니다."));
      PostAlcohol postAlcohol = PostAlcohol.of(post, alcohol, alcoholFeature);
      post.addPostAlcohol(postAlcohol);
      alcohol.addPostAlcohol(postAlcohol);
    }

    return AddPostResponseDto.of(post);
  }


  public void editPost(Long postNo, EditPostRequestDto editPostRequestDto) {
    Post post = postRepository.findByIdAndDelYnIs(postNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException(MessageUtil.POST_NOT_FOUND_MSG));
    if (StringUtils.isNotEmpty(editPostRequestDto.getPostContent())) {
      post.setContent(editPostRequestDto.getPostContent());
    }
    if (StringUtils.isNotEmpty(editPostRequestDto.getPostType())) {
      post.setContent(editPostRequestDto.getPostType());
    }
    if (StringUtils.isNotEmpty(editPostRequestDto.getPositionInfo())) {
      post.setContent(editPostRequestDto.getPositionInfo());
    }
    postRepository.save(post);

    List<String> tagStrList = editPostRequestDto.getTagList();
    Long alcoholNo = editPostRequestDto.getAlcoholNo();
    String alcoholFeature = editPostRequestDto.getAlcoholFeature();

    if (CollectionUtils.isNotEmpty(tagStrList)) {
      //FIXME 추후 최적화.
      List<Tag> tags = tagService.addOrSetTags(tagStrList);
      post.removeAllPostTagsAndAddNewPostTags(tags);
    }
    postRepository.save(post);

    if (alcoholNo != null && alcoholFeature != null) {
      Alcohol alcohol = alcoholRepository.findByIdAndDelYnIs(alcoholNo, YesOrNo.N).orElseThrow(
          () -> new IllegalArgumentException("술이 존재하지 않습니다."));
      post.removeAllPostAlcoholsAndAddNewPostAlcohols(List.of(alcohol), alcoholFeature);
    }
  }


  public void deletePost(Long postNo) {
    Post post = postRepository.findByIdAndDelYnIs(postNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException(MessageUtil.POST_NOT_FOUND_MSG));
    post.setDelYn(YesOrNo.Y);
  }

  public PostResponseDto getPostDetail(Long postNo, List<AttachDto> attachDtos) {
    Post post = postRepository.findByIdAndDelYnIs(postNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException(MessageUtil.POST_NOT_FOUND_MSG));
    Long userNo = post.getCreatedBy();
    User user = null;
    if (userNo != null) {
      user = userRepository.findByIdAndDelYnIs(userNo, YesOrNo.N).orElseThrow(
          () -> new IllegalArgumentException(MessageUtil.USER_NOT_FOUND_MSG));
    }
    Alcohol alcohol = null;
    List<PostAlcohol> postAlcohols = post.getPostAlcohols();
    if (CollectionUtils.isNotEmpty(postAlcohols)) {
      alcohol = postAlcohols.stream().filter(postAlcohol -> postAlcohol.getDelYn() == YesOrNo.N)
                            .map(PostAlcohol::getAlcohol).findFirst().orElse(null);
    }

    List<PostTag> nonDeletedPostTags =
        post.getPostTags().stream().filter(postTag -> postTag.getDelYn() == YesOrNo.N).toList();

    List<Tag> tags = tagRepository.findByPostTagsInAndDelYnIs(nonDeletedPostTags, YesOrNo.N);
    List<Comment> comments = commentRepository.findByPostAndDelYnIs(post, YesOrNo.N);
    if (attachDtos == null) {
      attachDtos =
          AttachDto.of(
              attachRepository.findByRefNoAndAttachTypeIsAndDelYnIs(post.getId(), AttachType.POST,
                  YesOrNo.N));
    }
    // isFollowedByMe, isLikedByMe, quoteInfo, likeCount, quoteCount
    Long myUserNo = AuthUtil.getUserNoFromAuthentication();
    Boolean isLikedByMe = null;
    Boolean isFollowdByMe = null;
    if (myUserNo != null) {
      isLikedByMe =
          postLikeRepository.findByUserAndPostAndDelYnIs(User.of(myUserNo), post, YesOrNo.N)
                            .isPresent();
      if (post.getCreatedBy() != null) {
        isFollowdByMe = userFollowRepository.findByFollowingAndFollowedAndDelYnIs(
            User.of(myUserNo), User.of(post.getCreatedBy()), YesOrNo.N).isPresent();
      }
    }
    Long likeCount = postLikeRepository.countByPostAndDelYnIs(post, YesOrNo.N);
    Long quoteCount = postQuoteRepository.countByPostAndDelYnIs(post, YesOrNo.N);

    return PostResponseDto.of(post, user, alcohol, tags, comments, attachDtos, isLikedByMe,
        isFollowdByMe, likeCount, quoteCount);
  }

  public PostResponseDto getPostDetail(Long postNo) {
    return getPostDetail(postNo, null);
  }

  public GetPostResponseDto getPosts(Long page, Long size, String orderColumn, String orderType,
                                     String searchKeyword, List<Long> searchUserNoList) {
    GetPostVo getPostVo = GetPostVo.builder()
                                   .page(page)
                                   .offset(page * size)
                                   .size(size)
                                   .orderColumn(orderColumn)
                                   .orderType(orderType)
                                   .searchKeyword(searchKeyword)
                                   .searchUserNoList(searchUserNoList)
                                   .build();
    List<GetPostsMvo> getPostsMvos = postMybatisRepository.getPosts(getPostVo);
    Long totalCount = postMybatisRepository.getPostsCount(getPostVo);
    List<Post> posts = getPostsMvos.stream().map(Post::of).toList();
    List<PostResponseDto> list =
        posts.stream().map(post -> getPostDetail(post.getId())).toList();
    // FIXME
    // 추구 여기서 첨부파일 개수 쿼리 최적화.
    return GetPostResponseDto.of(list, totalCount);
  }

  public AddCommentResponseDto addComment(Long postNo, AddCommentRequestDto addCommentRequestDto) {
    Post post = postRepository.findByIdAndDelYnIs(postNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException(MessageUtil.POST_NOT_FOUND_MSG));
    Comment comment = Comment.of(post, addCommentRequestDto.getCommentContent());
    post.addComment(comment);
    commentRepository.save(comment);

    return AddCommentResponseDto.of(comment);
  }

  public GetPostCommentsResponseDto getPostComments(Long postNo) {
    Post post = postRepository.findByIdAndDelYnIs(postNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException(MessageUtil.POST_NOT_FOUND_MSG));
    List<Comment> comments =
        post.getComments().stream().filter(comment -> comment.getDelYn() == YesOrNo.N).toList();
    return GetPostCommentsResponseDto.of(comments);
  }

  public void editComment(Long postNo, Long commentNo,
                          EditCommentRequestDto editCommentRequestDto) {
    postRepository.findByIdAndDelYnIs(postNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException(MessageUtil.POST_NOT_FOUND_MSG));
    Comment comment = commentRepository.findByIdAndDelYnIs(commentNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
    if (!ObjectUtils.isEmpty(editCommentRequestDto) &&
            StringUtils.isNotBlank(editCommentRequestDto.getCommentContent())) {
      comment.setContent(editCommentRequestDto.getCommentContent());
    }
  }

  public void deleteComment(Long postNo, Long commentNo) {
    postRepository.findByIdAndDelYnIs(postNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException(MessageUtil.POST_NOT_FOUND_MSG));
    Comment comment = commentRepository.findByIdAndDelYnIs(commentNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
    comment.setDelYn(YesOrNo.Y);
  }

  public PostQuote addQuote(Long postNo, Long quotedPostNo) {
    Post post = postRepository.findByIdAndDelYnIs(postNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException(MessageUtil.POST_NOT_FOUND_MSG));

    Post quotedPost = postRepository.findByIdAndDelYnIs(quotedPostNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException("인용할 게시글이 존재하지 않습니다."));
    PostQuote postQuote =
        postQuoteRepository.findByPostAndQuoteAndDelYnIs(post, quotedPost, YesOrNo.N)
                           .orElse(PostQuote.of(post, quotedPost));
    postQuoteRepository.save(postQuote);
    return postQuote;
  }

  public void deleteQuote(Long quoteNo) {
    PostQuote postQuote = postQuoteRepository.findByIdAndDelYnIs(quoteNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException("인용이 존재하지 않습니다."));
    postQuote.setDelYn(YesOrNo.Y);
  }

  public GetQuotesByPostResponseDto getQuotesByPost(Long postNo) {
    Post post = postRepository.findByIdAndDelYnIs(postNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException(MessageUtil.POST_NOT_FOUND_MSG));
    List<PostQuote> postQuotes = postQuoteRepository.findByPostAndDelYnIs(post, YesOrNo.N);
    return GetQuotesByPostResponseDto.of(postQuotes);
  }

  public void likePost(Long userNo, Long postNo) {
    User user = userRepository.findByIdAndDelYnIs(userNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException(MessageUtil.USER_NOT_FOUND_MSG));
    Post post = postRepository.findByIdAndDelYnIs(postNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException(MessageUtil.POST_NOT_FOUND_MSG));

    PostLike postLike =
        postLikeRepository.findByUserAndPostAndDelYnIs(user, post, YesOrNo.N).orElse(
            PostLike.of(user, post));
    postLikeRepository.save(postLike);
  }

  public void likeCancelPost(Long userNo, Long postNo) {
    User user = userRepository.findByIdAndDelYnIs(userNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException(MessageUtil.USER_NOT_FOUND_MSG));
    Post post = postRepository.findByIdAndDelYnIs(postNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException(MessageUtil.POST_NOT_FOUND_MSG));

    PostLike postLike =
        postLikeRepository.findByUserAndPostAndDelYnIs(user, post, YesOrNo.N).orElseThrow(
            () -> new IllegalArgumentException("좋아요가 존재하지 않습니다."));
    postLike.setDelYn(YesOrNo.Y);
  }
}

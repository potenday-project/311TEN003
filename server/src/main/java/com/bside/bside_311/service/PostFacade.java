package com.bside.bside_311.service;

import com.bside.bside_311.component.AlcoholManager;
import com.bside.bside_311.component.AttachManager;
import com.bside.bside_311.component.PostAlcoholManager;
import com.bside.bside_311.component.PostService;
import com.bside.bside_311.component.PostTagManager;
import com.bside.bside_311.component.TagManager;
import com.bside.bside_311.component.UserManager;
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
import com.bside.bside_311.dto.GetPostsToOneMvo;
import com.bside.bside_311.dto.GetQuotesByPostResponseDto;
import com.bside.bside_311.dto.PostResponseDto;
import com.bside.bside_311.dto.common.ResultDto;
import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.AttachType;
import com.bside.bside_311.entity.Comment;
import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.PostAlcohol;
import com.bside.bside_311.entity.PostLike;
import com.bside.bside_311.entity.PostQuote;
import com.bside.bside_311.entity.PostTag;
import com.bside.bside_311.entity.PostType;
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
import com.bside.bside_311.util.ResultCode;
import com.bside.bside_311.util.ValidateUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostFacade {
  private final UserManager userManager;
  private final TagManager tagManager;
  private final AttachManager attachManager;
  private final PostTagManager postTagManager;
  private final PostAlcoholManager postAlcoholManager;
  private final TagService tagService;
  private final AlcoholManager alcoholManager;
  private final UserRepository userRepository;
  private final PostLikeRepository postLikeRepository;
  private final PostService postService;
  private final PostRepository postRepository;
  private final PostMybatisRepository postMybatisRepository;
  private final TagRepository tagRepository;
  private final AttachRepository attachRepository;
  private final AlcoholRepository alcoholRepository;

  private final CommentRepository commentRepository;
  private final PostQuoteRepository postQuoteRepository;
  private final UserFollowRepository userFollowRepository;

  private Alcohol getAlcohol(Post post) {
    if (ObjectUtils.isEmpty(post) || CollectionUtils.isEmpty(post.getPostAlcohols())) {
      return null;
    }
    List<PostAlcohol> postAlcohols = post.getPostAlcohols();
    if (CollectionUtils.isNotEmpty(postAlcohols)) {
      return postAlcohols.stream().filter(postAlcohol -> postAlcohol.getDelYn() == YesOrNo.N)
                         .map(PostAlcohol::getAlcohol).findFirst().orElse(null);
    }
    return null;
  }

  public AddPostResponseDto addPost(
      Post post, Long alcoholNo, String alcoholFeature,
      List<String> tagStrList) {
    log.info(">>> PostService.addPost");
    if (CollectionUtils.isNotEmpty(tagStrList)) {
      tagManager.registerTagsToPost(post, tagStrList);
    }
    postService.savePost(post);
    if (alcoholNo != null) {
      alcoholManager.connectAlcoholWithPost(alcoholNo, alcoholFeature, post);
    }

    return AddPostResponseDto.of(post);
  }

  public void editPost(Long postNo, EditPostRequestDto editPostRequestDto) {
    Post post = postService.findPost(postNo);
    ValidateUtil.resourceChangeableCheckByThisRequestToken(post);
    if (StringUtils.isNotEmpty(editPostRequestDto.getPostContent())) {
      post.setContent(editPostRequestDto.getPostContent());
    }
    if (StringUtils.isNotEmpty(editPostRequestDto.getPostType())) {
      post.setPostType(PostType.valueOf(editPostRequestDto.getPostType()));
    }
    if (StringUtils.isNotEmpty(editPostRequestDto.getPositionInfo())) {
      post.setPosition(editPostRequestDto.getPositionInfo());
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

    if (alcoholNo != null) {
      Alcohol alcohol = alcoholRepository.findByIdAndDelYnIs(alcoholNo, YesOrNo.N).orElseThrow(
          () -> new IllegalArgumentException("술이 존재하지 않습니다."));
      post.removeAllPostAlcoholsAndAddNewPostAlcohols(List.of(alcohol), alcoholFeature);
    }
  }

  public void deletePost(Long postNo) {
    Post post = postService.findPost(postNo);
    ValidateUtil.resourceChangeableCheckByThisRequestToken(post);
    postService.deletePost(post);
    postTagManager.deletePostTagByPost(post);
    postAlcoholManager.deletePostAlcoholByPost(post);
    attachManager.deleteAttachesByRefNoAndAttachType(postNo, AttachType.POST);
  }

  public PostResponseDto getPostDetail(Long postNo) {
    Post post = postService.findPost(postNo);
    Long userNo = post.getCreatedBy();
    User user = userManager.getUser(userNo);
    Alcohol alcohol = getAlcohol(post);
    List<Tag> tags = getTags(post);
    List<Comment> comments = getComments(post);

    List<AttachDto> postAttachDtos =
        attachManager.getAttachListBykeyAndType(post.getId(), AttachType.POST);
    List<AttachDto> profileAttachDtos =
        // FIXME 이부분 그 전에 post.getId()여서 프로필 이미지를 불러오지 못했었음. 관련하여 테스트 코드 작성할것.
        attachManager.getAttachListBykeyAndType(post.getCreatedBy(), AttachType.PROFILE);
    // isFollowedByMe, isLikedByMe, quoteInfo, likeCount, quoteCount
    Long myUserNo = AuthUtil.getUserNoFromAuthentication();
    boolean isLikedByMe = getLikedByMe(post, myUserNo);
    boolean isFollowdByMe = getFollowedByMe(post, myUserNo);
    Long likeCount = getLikeCount(post);
    Long quoteCount = getQuoteCount(post);

    return PostResponseDto.of(post, user, alcohol, tags, comments, postAttachDtos,
        profileAttachDtos, isLikedByMe,
        isFollowdByMe, likeCount, quoteCount);
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

  public Page<PostResponseDto> getPostsV2(Pageable pageable,
                                          String searchKeyword, List<Long> searchUserNoList,
                                          Boolean isLikedByMe, Boolean isCommentedByMe,
                                          List<Long> searchAlcoholNoList) {
    Page<Post> posts =
        postService.getPostListCommon(pageable, searchKeyword, searchUserNoList, isLikedByMe,
            isCommentedByMe,
            searchAlcoholNoList);

    List<Long> postNos = posts.stream().map(Post::getId).toList();
    Map<Long, GetPostsToOneMvo> postsToOneMap =
        postService.getGetPostsToOneMvoMap(postNos);

    Map<Long, List<AttachDto>> pToAMap =
        attachManager.getAttachInfoMapBykeysAndType(postNos, AttachType.POST);

    List<Long> postCreatedBys = posts.stream().map(Post::getCreatedBy).toList();
    Map<Long, List<AttachDto>> uToAMap =
        attachManager.getAttachInfoMapBykeysAndType(postCreatedBys, AttachType.PROFILE);

    return posts.map(post -> {
      GetPostsToOneMvo getPostsToOneMvo = postsToOneMap.get(post.getId());
      List<AttachDto> postAttachDtos = pToAMap.getOrDefault(post.getId(), new ArrayList<>());
      List<AttachDto> userAttachDtos = uToAMap.getOrDefault(post.getCreatedBy(), new ArrayList<>());
      return PostResponseDto.of(post, getPostsToOneMvo, postAttachDtos, userAttachDtos);
    });
  }

  public ResultDto<Page<PostResponseDto>> getPostsPopular(Long page, Long size) {
    Page<Post> posts =
        postService.getPostListPopular(page, size);

    List<Long> postNos = posts.stream().map(Post::getId).toList();
    Map<Long, GetPostsToOneMvo> postsToOneMap =
        postService.getGetPostsToOneMvoMap(postNos);

    Map<Long, List<AttachDto>> pToAMap =
        attachManager.getAttachInfoMapBykeysAndType(postNos, AttachType.POST);

    List<Long> postCreatedBys = posts.stream().map(Post::getCreatedBy).toList();
    Map<Long, List<AttachDto>> uToAMap =
        attachManager.getAttachInfoMapBykeysAndType(postCreatedBys, AttachType.PROFILE);

    return ResultDto.of(ResultCode.SUCCESS, posts.map(post -> {
      GetPostsToOneMvo getPostsToOneMvo = postsToOneMap.get(post.getId());
      List<AttachDto> postAttachDtos = pToAMap.getOrDefault(post.getId(), new ArrayList<>());
      List<AttachDto> userAttachDtos = uToAMap.getOrDefault(post.getCreatedBy(), new ArrayList<>());
      return PostResponseDto.of(post, getPostsToOneMvo, postAttachDtos, userAttachDtos);
    }));
  }

  public AddCommentResponseDto addComment(Long postNo, AddCommentRequestDto addCommentRequestDto) {
    Post post = postService.findPost(postNo);
    Comment comment = Comment.of(post, addCommentRequestDto.getCommentContent());
    post.addComment(comment);
    commentRepository.save(comment);

    return AddCommentResponseDto.of(comment);
  }

  public GetPostCommentsResponseDto getPostComments(Long postNo) {
    Post post = postService.findPost(postNo);
    List<Comment> comments =
        post.getComments().stream().filter(comment -> comment.getDelYn() == YesOrNo.N).toList();
    // FIXME 최적화. 추후 페이징 처리할것.
    List<Long> commentCreatedList = comments.stream().map(Comment::getCreatedBy).toList();
    List<User> userList =
        userRepository.findAllByIdInAndDelYnIs(commentCreatedList, YesOrNo.N);
    Map<Long, User> createdByToUser = new HashMap<>();
    for (User user : userList) {
      createdByToUser.put(user.getId(), user);
    }

    Map<Long, List<AttachDto>> uToAMap =
        attachManager.getAttachInfoMapBykeysAndType(commentCreatedList, AttachType.PROFILE);

    return GetPostCommentsResponseDto.of(comments, createdByToUser, uToAMap);
  }

  public void editComment(Long postNo, Long commentNo,
                          EditCommentRequestDto editCommentRequestDto) {
    postService.findPost(postNo);
    Comment comment = commentRepository.findByIdAndDelYnIs(commentNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
    ValidateUtil.resourceChangeableCheckByThisRequestToken(comment);
    if (!ObjectUtils.isEmpty(editCommentRequestDto) &&
            StringUtils.isNotBlank(editCommentRequestDto.getCommentContent())) {
      comment.setContent(editCommentRequestDto.getCommentContent());
    }
  }

  public void deleteComment(Long postNo, Long commentNo) {
    postService.findPost(postNo);
    Comment comment = commentRepository.findByIdAndDelYnIs(commentNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
    ValidateUtil.resourceChangeableCheckByThisRequestToken(comment);
    comment.setDelYn(YesOrNo.Y);
  }

  public PostQuote addQuote(Long postNo, Long quotedPostNo) {
    Post post = postService.findPost(postNo);

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
    ValidateUtil.resourceChangeableCheckByThisRequestToken(postQuote);
    postQuote.setDelYn(YesOrNo.Y);
  }

  public GetQuotesByPostResponseDto getQuotesByPost(Long postNo) {
    Post post = postService.findPost(postNo);
    List<PostQuote> postQuotes = postQuoteRepository.findByPostAndDelYnIs(post, YesOrNo.N);
    return GetQuotesByPostResponseDto.of(postQuotes);
  }

  public void likePost(Long userNo, Long postNo) {
    User user = userManager.getUser(userNo);
    Post post = postService.findPost(postNo);

    PostLike postLike =
        postLikeRepository.findByUserAndPostAndDelYnIs(user, post, YesOrNo.N).orElse(
            PostLike.of(user, post));
    postLikeRepository.save(postLike);
  }

  public void likeCancelPost(Long userNo, Long postNo) {
    User user = userManager.getUser(userNo);
    Post post = postService.findPost(postNo);

    PostLike postLike =
        postLikeRepository.findByUserAndPostAndDelYnIs(user, post, YesOrNo.N).orElseThrow(
            () -> new IllegalArgumentException("좋아요가 존재하지 않습니다."));
    ValidateUtil.resourceChangeableCheckByThisRequestToken(postLike);
    postLike.setDelYn(YesOrNo.Y);
  }

  private Long getQuoteCount(Post post) {
    return postQuoteRepository.countByPostAndDelYnIs(post, YesOrNo.N);
  }

  private Long getLikeCount(Post post) {
    return postLikeRepository.countByPostAndDelYnIs(post, YesOrNo.N);
  }

  @Nullable
  private boolean getFollowedByMe(Post post, Long myUserNo) {
    if (ObjectUtils.isEmpty(post) || ObjectUtils.isEmpty(post.getCreatedBy()) || ObjectUtils
                                                                                     .isEmpty(
                                                                                         myUserNo)) {
      return false;
    }
    return userFollowRepository.findByFollowingAndFollowedAndDelYnIs(
        User.of(myUserNo), User.of(post.getCreatedBy()), YesOrNo.N).isPresent();
  }

  @Nullable
  private boolean getLikedByMe(Post post, Long myUserNo) {
    if (ObjectUtils.isEmpty(post) || ObjectUtils.isEmpty(post.getId()) ||
            ObjectUtils.isEmpty(myUserNo)) {
      return false;
    }
    return postLikeRepository.findByUserAndPostAndDelYnIs(User.of(myUserNo), post, YesOrNo.N)
                             .isPresent();
  }

  private List<Comment> getComments(Post post) {
    return commentRepository.findByPostAndDelYnIs(post, YesOrNo.N);
  }

  private List<Tag> getTags(Post post) {
    if (ObjectUtils.isEmpty(post) || CollectionUtils.isEmpty(post.getPostTags())) {
      return List.of();
    }
    List<PostTag> nonDeletedPostTags =
        post.getPostTags().stream().filter(postTag -> postTag.getDelYn() == YesOrNo.N).toList();
    return tagRepository.findByPostTagsInAndDelYnIs(nonDeletedPostTags, YesOrNo.N);
  }
}

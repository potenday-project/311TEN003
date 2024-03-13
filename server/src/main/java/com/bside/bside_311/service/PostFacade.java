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
import com.bside.bside_311.model.AbstractUserAuthInfo;
import com.bside.bside_311.model.UserAuthInfo;
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
  private final PostService postService;
  private final PostMybatisRepository postMybatisRepository;
  private final AlcoholService alcoholService;

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

  public void editPost(Long postNo, EditPostRequestDto editPostRequestDto,
                       UserAuthInfo accessUser) {
    Post post = postService.findPost(postNo);
    ValidateUtil.resourceChangeableCheckByThisUserAuthInfo(post, accessUser);
    if (StringUtils.isNotEmpty(editPostRequestDto.getPostContent())) {
      post.setContent(editPostRequestDto.getPostContent());
    }
    if (StringUtils.isNotEmpty(editPostRequestDto.getPostType())) {
      post.setPostType(PostType.valueOf(editPostRequestDto.getPostType()));
    }
    if (StringUtils.isNotEmpty(editPostRequestDto.getPositionInfo())) {
      post.setPosition(editPostRequestDto.getPositionInfo());
    }
    postService.savePost(post);

    List<String> tagStrList = editPostRequestDto.getTagList();
    Long alcoholNo = editPostRequestDto.getAlcoholNo();
    String alcoholFeature = editPostRequestDto.getAlcoholFeature();

    if (CollectionUtils.isNotEmpty(tagStrList)) {
      //FIXME 추후 최적화.
      postService.putTagsToPost(post, tagStrList);
    }
    postService.savePost(post);

    if (alcoholNo != null) {
      Alcohol alcohol = alcoholService.findAlcoholById(alcoholNo);
      postService.linkAlcoholToPost(post, alcohol, alcoholFeature);
    }
  }

  public void deletePost(Long postNo, AbstractUserAuthInfo accessUserAuthInfo) {
    Post post = postService.findPost(postNo);
    ValidateUtil.resourceChangeableCheckByThisUserAuthInfo(post, accessUserAuthInfo);
    postService.deletePost(post);
    postTagManager.deletePostTagByPost(post);
    postAlcoholManager.deletePostAlcoholByPost(post);
    attachManager.deleteAttachesByRefNoAndAttachType(postNo, AttachType.POST, accessUserAuthInfo);
  }

  public PostResponseDto getPostDetail(Long postNo, Long myUserNo) {
    Post post = postService.findPost(postNo);
    Long userNo = post.getCreatedBy();
    User user = userManager.getUser(userNo);
    Alcohol alcohol = getAlcohol(post);
    List<Tag> tags = getTags(post);
    List<Comment> comments = getComments(post);

    List<AttachDto> postAttachDtos =
        attachManager.getAttachListBykeyAndType(post.getId(), AttachType.POST);
    List<AttachDto> profileAttachDtos =
        attachManager.getAttachListBykeyAndType(post.getCreatedBy(), AttachType.PROFILE);
    // isFollowedByMe, isLikedByMe, quoteInfo, likeCount, quoteCount
    boolean isLikedByMe = postService.isThisPostLikedByMe(post, myUserNo);
    boolean isFollowdByMe = postService.getFollowedByMe(post, myUserNo);
    Long likeCount = postService.getLikeCount(post);
    Long quoteCount = postService.getQuoteCount(post);

    return PostResponseDto.of(post, user, alcohol, tags, comments, postAttachDtos,
        profileAttachDtos, isLikedByMe,
        isFollowdByMe, likeCount, quoteCount);
  }

  /**
   * 게시글 조회.
   * @deprecated 이 메소드는 사용되지 않습니다. {@link PostFacade#getPostsV2(Pageable, String, List, Boolean, Boolean, List)}를 사용하세요.
   */
  public GetPostResponseDto getPosts(Long page, Long size, String orderColumn, String orderType,
                                     String searchKeyword, List<Long> searchUserNoList, Long myUserNo) {
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
        posts.stream().map(post -> getPostDetail(post.getId(), myUserNo)).toList();
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
    postService.addCommentToPost(post, comment);
    return AddCommentResponseDto.of(comment);
  }

  public GetPostCommentsResponseDto getPostComments(Long postNo) {
    Post post = postService.findPost(postNo);

    List<Comment> comments =
        post.getComments().stream().filter(comment -> comment.getDelYn() == YesOrNo.N).toList();
    // FIXME 최적화. 추후 페이징 처리할것.
    List<Long> commentCreatedList = comments.stream().map(Comment::getCreatedBy).toList();
    List<User> userList = userManager.findUsers(commentCreatedList);
    Map<Long, User> createdByToUser = new HashMap<>();
    for (User user : userList) {
      createdByToUser.put(user.getId(), user);
    }

    Map<Long, List<AttachDto>> uToAMap =
        attachManager.getAttachInfoMapBykeysAndType(commentCreatedList, AttachType.PROFILE);

    return GetPostCommentsResponseDto.of(comments, createdByToUser, uToAMap);
  }

  public void editComment(Long postNo, Long commentNo,
                          EditCommentRequestDto editCommentRequestDto,
                          AbstractUserAuthInfo accessUserAuthInfo) {
    postService.findPost(postNo);
    Comment comment = postService.getCommentById(commentNo);
    ValidateUtil.resourceChangeableCheckByThisUserAuthInfo(comment, accessUserAuthInfo);
    if (!ObjectUtils.isEmpty(editCommentRequestDto) &&
            StringUtils.isNotBlank(editCommentRequestDto.getCommentContent())) {
      comment.setContent(editCommentRequestDto.getCommentContent());
    }
  }

  public void deleteComment(Long postNo, Long commentNo, AbstractUserAuthInfo accessUserAuthInfo) {
    postService.findPost(postNo);
    Comment comment = postService.getCommentById(commentNo);
    ValidateUtil.resourceChangeableCheckByThisUserAuthInfo(comment, accessUserAuthInfo);
    comment.setDelYn(YesOrNo.Y);
  }

  public PostQuote addQuote(Long postNo, Long quotedPostNo) {
    Post post = postService.findPost(postNo);
    Post quotedPost = postService.findPost(quotedPostNo, "인용할 게시글이 존재하지 않습니다.");
    return postService.addQuoteToPost(post, quotedPost);
  }

  public void deleteQuote(Long quoteNo, AbstractUserAuthInfo accessUserAuthInfo) {
    postService.deleteQuote(quoteNo, accessUserAuthInfo);
  }

  public GetQuotesByPostResponseDto getQuotesByPost(Long postNo) {
    Post post = postService.findPost(postNo);
    return postService.getQuoteByPost(post);
  }

  public void likePost(Long userNo, Long postNo) {
    User user = userManager.getUser(userNo);
    Post post = postService.findPost(postNo);
    postService.userLikePost(post, user);
  }

  public void likeCancelPost(Long userNo, Long postNo, AbstractUserAuthInfo accessUserAuthInfo) {
    User user = userManager.getUser(userNo);
    Post post = postService.findPost(postNo);
    postService.userCancelLikePost(post, user, accessUserAuthInfo);
  }



  private List<Comment> getComments(Post post) {
    return postService.getComments(post);

  }

  private List<Tag> getTags(Post post) {
    if (ObjectUtils.isEmpty(post) || CollectionUtils.isEmpty(post.getPostTags())) {
      return List.of();
    }
    return tagService.getTags(post);
  }
}

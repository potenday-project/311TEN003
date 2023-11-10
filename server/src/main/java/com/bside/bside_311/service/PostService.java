package com.bside.bside_311.service;

import com.bside.bside_311.dto.AddPostResponseDto;
import com.bside.bside_311.dto.EditPostRequestDto;
import com.bside.bside_311.dto.GetPostResponseDto;
import com.bside.bside_311.dto.GetPostVo;
import com.bside.bside_311.dto.GetPostsMvo;
import com.bside.bside_311.dto.PostResponseDto;
import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.PostAlcohol;
import com.bside.bside_311.entity.PostTag;
import com.bside.bside_311.entity.Tag;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.AlcoholRepository;
import com.bside.bside_311.repository.PostMybatisRepository;
import com.bside.bside_311.repository.PostRepository;
import com.bside.bside_311.repository.TagRepository;
import com.bside.bside_311.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {
  private final TagService tagService;
  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final PostMybatisRepository postMybatisRepository;
  private final TagRepository tagRepository;
  private final AlcoholRepository alcoholRepository;

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
    if (alcoholNo != null && alcoholFeature != null) {
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
        () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    postRepository.save(post);

    List<String> tagStrList = editPostRequestDto.getTagList();
    Long alcoholNo = editPostRequestDto.getAlcoholNo();
    String alcoholFeature = editPostRequestDto.getAlcoholFeature();

    if (CollectionUtils.isNotEmpty(tagStrList)) {
      //FIXME 추후 최적화.
      List<Tag> tags = tagService.addOrSetTags(tagStrList);
      post.removeAllPostTagsAndAddNewPostTags(tags);
    }
    if (alcoholNo != null && alcoholFeature != null) {
      Alcohol alcohol = alcoholRepository.findByIdAndDelYnIs(alcoholNo, YesOrNo.N).orElseThrow(
          () -> new IllegalArgumentException("술이 존재하지 않습니다."));
      post.removeAllPostAlcoholsAndAddNewPostAlcohols(List.of(alcohol), alcoholFeature);
    }
  }


  public void deletePost(Long postNo) {
    Post post = postRepository.findByIdAndDelYnIs(postNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    post.setDelYn(YesOrNo.Y);
  }

//  private void addOrSetPost(Post post, Long alcoholNo, String alcoholFeature,
//                            List<String> tagList) {
//    postRepository.save(post);
//    if (CollectionUtils.isNotEmpty(tagList)) {
//      for (String tagName : tagList) {
//        Tag tag = tagRepository.findByNameAndDelYnIs(tagName, YesOrNo.N).orElse(Tag.of());
//        tagRepository.save(tag);
//      }
//    }
//    if (alcoholNo != null && alcoholFeature != null) {
//      Alcohol alcohol = alcoholRepository.findByIdAndDelYnIs(alcoholNo, YesOrNo.N).orElseThrow(
//          () -> new IllegalArgumentException("술이 존재하지 않습니다."));
//      PostAlcohol postAlcohol = PostAlcohol.of(post, alcohol, alcoholFeature);
//      post.addPostAlcohol(postAlcohol);
//      alcohol.addPostAlcohol(postAlcohol);
//    }
//  }


  public PostResponseDto getPostDetail(Long postNo) {
    Post post = postRepository.findByIdAndDelYnIs(postNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    Long userNo = post.getCreatedBy();
    User user = null;
    if (userNo != null) {
      user = userRepository.findByIdAndDelYnIs(userNo, YesOrNo.N).orElseThrow(
          () -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }
    Alcohol alcohol = null;
    List<PostAlcohol> postAlcohols = post.getPostAlcohols();
    if (CollectionUtils.isNotEmpty(postAlcohols)) {
      alcohol = postAlcohols.get(0).getAlcohol();
    }

    List<PostTag> nonDeletedPostTags =
        post.getPostTags().stream().filter(postTag -> postTag.getDelYn() == YesOrNo.N).collect(
            Collectors.toList());

    List<Tag> tags = tagRepository.findByPostTagsInAndDelYnIs(nonDeletedPostTags, YesOrNo.N);

    return PostResponseDto.of(post, user, alcohol, tags);
  }

  public GetPostResponseDto getPosts(Long page, Long size, String orderColumn, String orderType,
                                     String searchKeyword) {
    GetPostVo getPostVo = GetPostVo.builder()
                                   .page(page)
                                   .offset(page * size)
                                   .size(size)
                                   .orderColumn(orderColumn)
                                   .orderType(orderType)
                                   .searchKeyword(searchKeyword)
                                   .build();
    List<GetPostsMvo> getPostsMvos = postMybatisRepository.getPosts(getPostVo);
    Long totalCount = postMybatisRepository.getPostsCount(getPostVo);
    List<Post> posts = getPostsMvos.stream().map(Post::of).collect(Collectors.toList());
    List<PostResponseDto> list =
        posts.stream().map(post -> getPostDetail(post.getId())).collect(Collectors.toList());
    return GetPostResponseDto.of(list, totalCount);
  }
}

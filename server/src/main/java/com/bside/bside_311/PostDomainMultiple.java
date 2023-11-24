package com.bside.bside_311;

import com.bside.bside_311.dto.GetPostVo;
import com.bside.bside_311.dto.GetPostsMvo;
import com.bside.bside_311.dto.PostResponseDto;
import com.bside.bside_311.entity.Post;
import com.bside.bside_311.repository.PostMybatisRepository;
import com.bside.bside_311.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@AllArgsConstructor
public class PostDomainMultiple {
  private final PostRepository postRepository;
  private final PostMybatisRepository postMybatisRepository;
  List<PostDomain> postDomainList;
//  List<PostResponseDto> postDomainList;
  Long totalCount;



  public static PostDomainMultiple init(PostRepository postRepository, PostMybatisRepository postMybatisRepository){
    return PostDomainMultiple.builder().postRepository(postRepository).postMybatisRepository(postMybatisRepository).build();
  }

  public static  PostDomainMultiple of(GetPostVo getPostVo, PostRepository postRepository, PostMybatisRepository postMybatisRepository) {
    PostDomainMultiple postDomainMultiple = init(postRepository, postMybatisRepository);
    List<GetPostsMvo> getPostsMvos = postMybatisRepository.getPosts(getPostVo);
    Long totalCount = postMybatisRepository.getPostsCount(getPostVo);
    List<Post> posts = getPostsMvos.stream().map(Post::of).toList();
    List<PostDomain> list = posts.stream().map(post -> {
      try {
        return PostDomain.of(post);
      } catch (CloneNotSupportedException e) {
        throw new RuntimeException(e);
      }
    }).toList();
    postDomainMultiple.setPostDomainList(list);
    postDomainMultiple.setTotalCount(totalCount);
    return postDomainMultiple;
  }
}

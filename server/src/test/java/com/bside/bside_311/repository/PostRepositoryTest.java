package com.bside.bside_311.repository;

import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.PostLike;
import com.bside.bside_311.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
//@Rollback(false)
class PostRepositoryTest {
  @Autowired
  UserRepository userRepository;

  @Autowired
  PostRepository postRepository;

  @Autowired
  PostLikeRepository postLikeRepository;

  @PersistenceContext
  private EntityManager em;

  @Test
  void searchPagePopular() {
    //given
    // 게시글이 3개 정도 있다.
    // 그런데 그 게시글들은 좋아요 수가 아래와 같음.
    // 1st idx 게시글 : 좋아요 1개.
    // 2st idx 게시글 : 좋아요 3개.
    // 3st idx 게시글 : 좋아요 2개.
    List<Post> inputPosts = dataInitSearchPagePostPopular();


    em.flush();
    em.clear();

    //when
    Page<Post> posts = postRepository.searchPagePopular(0L, 10L);


    //then
    List<Post> content = posts.getContent();
    content.forEach(System.out::println);
    Assertions.assertThat(posts.getTotalElements()).isEqualTo(3);
    Assertions.assertThat(content.get(0).getId()).isEqualTo(inputPosts.get(1).getId());
  }

  @Test
  void searchPagePopularPageTest() {
    //given
    // 게시글이 3개 정도 있다.
    // 그런데 그 게시글들은 좋아요 수가 아래와 같음.
    // 1st idx 게시글 : 좋아요 1개.
    // 2st idx 게시글 : 좋아요 3개.
    // 3st idx 게시글 : 좋아요 2개.
    List<Post> inputPosts = dataInitSearchPagePostPopular();

    //when
    Page<Post> posts = postRepository.searchPagePopular(1L, 2L);


    //then
    List<Post> content = posts.getContent();
    content.forEach(System.out::println);
    Assertions.assertThat(posts.getTotalElements()).isEqualTo(3);
    Assertions.assertThat(content.get(0).getId()).isEqualTo(inputPosts.get(0).getId());
    // 이 뜻은 사이즈 크기가 2인데, 1번째 페이지를 보여달라는 뜻.(0부터 시작.)
    // 그러니까 지금 데이터 순서가 (idx 기준) 2, 1, 0 이니까, 0번째 인덱스의 데이터를 가리키는 것이 맞음.
  }

  private List<Post> dataInitSearchPagePostPopular() {
    List<User> userList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      User test = User.builder().id(i + 1L)
                      .userId(String.format("test%d", i))
                      .nickname(String.format("test%d", i))
                      .password("test1!").build();
      userList.add(test);
    }
    userRepository.saveAllAndFlush(userList);

    List<Post> inputPosts = new ArrayList<>();
    Post p1 = Post.builder().content("1st contest hello").build();
    Post p2 = Post.builder().content("2st contest hi").build();
    Post p3 = Post.builder().content("3rd contest goodBye").build();
    inputPosts.add(p1);
    inputPosts.add(p2);
    inputPosts.add(p3);
    postRepository.saveAllAndFlush(inputPosts);

    List<PostLike> postLikeList = new ArrayList<>();
    postLikeList.add(PostLike.of(userList.get(0), p1));
    postLikeList.add(PostLike.of(userList.get(0), p2));
    postLikeList.add(PostLike.of(userList.get(1), p2));
    postLikeList.add(PostLike.of(userList.get(2), p2));
    postLikeList.add(PostLike.of(userList.get(3), p3));
    postLikeList.add(PostLike.of(userList.get(4), p3));
    postLikeRepository.saveAllAndFlush(postLikeList);
    return inputPosts;
  }

}
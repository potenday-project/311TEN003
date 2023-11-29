package com.bside.bside_311.entity;

import com.bside.bside_311.dto.AddPostRequestDto;
import com.bside.bside_311.dto.GetPostsMvo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  @Column(name = "post_no")
  private Long id;

  private String content;


  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private PostType postType;

  private String position;

  @Builder.Default
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostLike> postLikes = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostTag> postTags = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostAlcohol> postAlcohols = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostQuote> postQuoteIngs = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "quote", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostQuote> postQuoteEds = new ArrayList<>();


  public static Post of(AddPostRequestDto addPostRequestDto) {
    Post post = Post.builder()
                    .content(addPostRequestDto.getPostContent())
                    .postType(PostType.valueOf(addPostRequestDto.getPostType()))
                    .position(addPostRequestDto.getPositionInfo())
                    .comments(new ArrayList<>())
                    .postLikes(new ArrayList<>())
                    .postTags(new ArrayList<>())
                    .postAlcohols(new ArrayList<>())
                    .build();
    return post;
  }

  public static Post of(Post post) throws CloneNotSupportedException {
    return (Post) post.clone();
  }

  public static Post of(GetPostsMvo getPostsMvo) {

    Post post = Post.builder()
                    .id(getPostsMvo.getPostNo())
                    .content(getPostsMvo.getContent())
                    .postType(PostType.valueOf(getPostsMvo.getType()))
                    .position(getPostsMvo.getPosition())
                    .comments(new ArrayList<>())
                    .postLikes(new ArrayList<>())
                    .postTags(new ArrayList<>())
                    .postAlcohols(new ArrayList<>())
                    .build();
    return post;
  }

  // 더 추상화.
  public void removeAllPostTagsAndAddNewPostTags(List<Tag> tags) {
    List<PostTag> previousPostTags = this.getPostTags();
    for (PostTag postTag : previousPostTags) {
      postTag.setDelYn(YesOrNo.Y);
    }
    tags.forEach(tag -> {
      PostTag postTag = PostTag.of(this, tag);
      this.addPostTag(postTag);
      tag.addPostTag(postTag);
    });
  }

  public void removeAllPostAlcoholsAndAddNewPostAlcohols(List<Alcohol> alcohols,
                                                         String alcoholFeature) {
    List<PostAlcohol> previousPostAlcoholss = this.getPostAlcohols();
    for (PostAlcohol postAlcohol : previousPostAlcoholss) {
      postAlcohol.setDelYn(YesOrNo.Y);
    }
    alcohols.forEach(alcohol -> {
      PostAlcohol postAlcohol = PostAlcohol.of(this, alcohol, alcoholFeature);
      this.addPostAlcohol(postAlcohol);
      alcohol.addPostAlcohol(postAlcohol);
    });
  }

  // 연관관계 편의 메서드.
  public void addComment(Comment comment) {
    if (!ObjectUtils.isEmpty(comment)) {
      this.comments.add(comment);
      comment.setPost(this);
    }
  }

  public void addPostLike(PostLike postLike) {
    if (!ObjectUtils.isEmpty(postLike)) {
      this.postLikes.add(postLike);
      postLike.setPost(this);
    }
  }

  public void addPostTag(PostTag postTag) {
    if (!ObjectUtils.isEmpty(postTag)) {
      this.postTags.add(postTag);
      postTag.setPost(this);
    }

  }

  public void addPostAlcohol(PostAlcohol postAlcohol) {
    if (postAlcohol != null) {
      this.postAlcohols.add(postAlcohol);
      postAlcohol.setPost(this);
    }
  }

  public void addPostQuoteIng(PostQuote postQuote) {
    if (postQuote != null) {
      this.postQuoteIngs.add(postQuote);
      postQuote.setPost(this);
    }
  }

  public void addPostQuoteEds(PostQuote postQuote) {
    if (postQuote != null) {
      this.postQuoteEds.add(postQuote);
      postQuote.setQuote(this);
    }
  }
}

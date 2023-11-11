package com.bside.bside_311.entity;

import com.bside.bside_311.dto.UserSignupRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  @Column(name = "user_no")
  private Long id;
  private String email;
  private String password;
  private String userId;
  private String nickname;

  private String introduction;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private Role role;

  // relation

  @Builder.Default
  @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserFollow> followingList = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserFollow> followedList = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostLike> postLikeList = new ArrayList<>();


  public User(Long id, String email, String password, String userId, String nickname,
              String introduction, Role role, List<UserFollow> followingList,
              List<UserFollow> followedList, List<PostLike> postLikeList) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.userId = userId;
    this.nickname = nickname;
    this.introduction = introduction;
    this.role = role;
    this.followingList = followingList;
    this.followedList = followedList;
    this.postLikeList = postLikeList;
  }

  public static User of(UserSignupRequestDto userSignupRequestDto) {
    return User.builder().email(userSignupRequestDto.getEmail())
               .password(userSignupRequestDto.getPassword())
               .userId(userSignupRequestDto.getId())
               .nickname(userSignupRequestDto.getNickname())
               .role(Role.ROLE_USER)
               .build();
  }

  public static User of(Long userNo) {
    return User.builder().id(userNo).build();
  }

  // bi-directional convenience method
  private void addFollowing(UserFollow userFollow) {
    if (!ObjectUtils.isEmpty(userFollow)) {
      this.followingList.add(userFollow);
      userFollow.setFollowing(this);
    }
  }

  private void addFollowed(UserFollow userFollow) {
    if (ObjectUtils.isEmpty(userFollow)) {
      this.followedList.add(userFollow);
      userFollow.setFollowed(this);
    }
  }

  private void addPostLike(PostLike postLike) {
    if (!ObjectUtils.isEmpty(postLike)) {
      this.postLikeList.add(postLike);
      postLike.setUser(this);
    }
  }


}

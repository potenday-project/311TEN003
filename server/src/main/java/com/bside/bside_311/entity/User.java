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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity{
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

  @OneToMany(mappedBy = "following", cascade = CascadeType.ALL)
  private List<UserFollow> followingList = new ArrayList<>();

  @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL)
  private List<UserFollow> followedList = new ArrayList<>();

  // bi-directional convenience method
  private void addFollowing(UserFollow userFollow){
    this.followingList.add(userFollow);
    userFollow.setFollowing(this);
  }

  private void addFollowed(UserFollow userFollow) {
    this.followedList.add(userFollow);
    userFollow.setFollowed(this);
  }


  @Builder
  public User(Long id, String email, String password, String userId, String nickname, Role role, String introduction) {
    super();
    this.id = id;
    this.email = email;
    this.password = password;
    this.userId = userId;
    this.nickname = nickname;
    this.role = role;
    this.introduction = introduction;
  }

  public static User of(UserSignupRequestDto userSignupRequestDto){
    return User.builder().email(userSignupRequestDto.getEmail())
        .password(userSignupRequestDto.getPassword())
        .userId(userSignupRequestDto.getId())
        .nickname(userSignupRequestDto.getNickname())
        .role(Role.ROLE_USER)
        .build();
  }


}

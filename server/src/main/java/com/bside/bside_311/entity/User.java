package com.bside.bside_311.entity;

import com.bside.bside_311.dto.UserSignupRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity{
  @Id
  @GeneratedValue
  private Long userNo;
  private String email;
  private String password;
  private String id;
  private String nickname;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private Role role;

  @Builder
  public User(Long userNo, String email, String password, String id, String nickname, Role role) {
    super();
    this.userNo = userNo;
    this.email = email;
    this.password = password;
    this.id = id;
    this.nickname = nickname;
    this.role = role;

  }

  public static User of(UserSignupRequestDto userSignupRequestDto){
    return User.builder().email(userSignupRequestDto.getEmail())
        .password(userSignupRequestDto.getPassword())
        .id(userSignupRequestDto.getId())
        .nickname(userSignupRequestDto.getNickname())
        .role(Role.ROLE_USER)
        .build();
  }
}

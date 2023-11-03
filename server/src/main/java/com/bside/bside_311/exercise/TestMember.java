package com.bside.bside_311.exercise;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","username","age"})
public class TestMember {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  private String username;

  private int age;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private TestTeam testTeam;

  public TestMember(String username) {
    this(username,0);
  }

  public TestMember(String username, int age) {
    this(username, age,null);
  }

  public TestMember(String username, int age, TestTeam testTeam) {
    this.username = username;
    this.age = age;

    if (testTeam != null) {
      changeTeam(testTeam);
    }
  }

  public void changeTeam(TestTeam testTeam) {
    this.testTeam = testTeam;
    testTeam.getTestMembers().add(this);
  }
}

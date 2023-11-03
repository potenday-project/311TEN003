package com.bside.bside_311.exercise;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","name"})
public class TestTeam {

  @Id
  @GeneratedValue
  @Column(name = "team_id")
  private Long id;
  private String name;

  @OneToMany(mappedBy = "testTeam")
  private List<TestMember> testMembers = new ArrayList<>();

  public TestTeam(String name) {
    this.name = name;
  }
}

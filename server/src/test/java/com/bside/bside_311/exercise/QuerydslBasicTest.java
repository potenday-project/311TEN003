package com.bside.bside_311.exercise;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.bside.bside_311.exercise.QTestMember.testMember;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

  @PersistenceContext
  EntityManager em;

  JPAQueryFactory query;

  @BeforeEach
  void before() {
    query = new JPAQueryFactory(em);
    TestTeam teamA = new TestTeam("teamA");
    TestTeam teamB = new TestTeam("teamB");

    em.persist(teamA);
    em.persist(teamB);
    TestMember member1 = new TestMember("member1", 10, teamA);
    TestMember member2 = new TestMember("member2", 20, teamA);
    TestMember member3 = new TestMember("member3", 30, teamB);
    TestMember member4 = new TestMember("member4", 40, teamB);
    em.persist(member1);
    em.persist(member2);
    em.persist(member3);
    em.persist(member4);
  }

  @Test
  void startJPQL() {
    TestMember findMember = em.createQuery("select m from TestMember m where m.username = :username", TestMember.class)
                          .setParameter("username", "member1")
                          .getSingleResult();

    assertThat(findMember.getUsername()).isEqualTo("member1");
  }

  @Test
  void startQuerydsl() {
    TestMember findMember = query.select(testMember)
                             .from(testMember)
                             .where(testMember.username.eq("member1"))
                             .fetchOne();

    assertThat(findMember.getUsername()).isEqualTo("member1");
  }
}

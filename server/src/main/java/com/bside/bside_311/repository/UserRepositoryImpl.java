package com.bside.bside_311.repository;

import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.support.Querydsl4RepositorySupport;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.function.Function;

import static com.bside.bside_311.entity.QUser.user;
import static com.bside.bside_311.entity.QUserFollow.userFollow;

public class UserRepositoryImpl extends Querydsl4RepositorySupport
    implements UserRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  public UserRepositoryImpl(EntityManager em) {
    super(User.class);
    this.queryFactory = new JPAQueryFactory(em);
  }


  @Override
  public Page<User> getMyFollowingUsersPage(Long myUserNo, Pageable pageable) {
    Function<JPAQueryFactory, JPAQuery> jpaQueryFactoryJPAQueryFunction =
        query -> {
          JPAQuery<User> jpaQuery = query.select(user)
                                         .from(user)
                                         .where(user.delYn.eq(YesOrNo.N).and(
                                             user.id.in(
                                                 JPAExpressions.select(
                                                                   userFollow.followed.id)
                                                               .from(userFollow)
                                                               .where(
                                                                   userFollow.following.id.eq(
                                                                                 myUserNo)
                                                                                          .and(
                                                                                              userFollow.delYn.eq(
                                                                                                  YesOrNo.N)))
                                             )));
          return jpaQuery;
        };

    return applyPagination(pageable, jpaQueryFactoryJPAQueryFunction);
  }

  @Override
  public Page<User> getUsersOfFollowingMePage(Long myUserNo, Pageable pageable) {
    Function<JPAQueryFactory, JPAQuery> jpaQueryFactoryJPAQueryFunction =
        query -> {
          JPAQuery<User> jpaQuery = query.select(user)
                                         .from(user)
                                         .where(user.delYn.eq(YesOrNo.N).and(
                                             user.id.in(
                                                 JPAExpressions.select(
                                                                   userFollow.following.id)
                                                               .from(userFollow)
                                                               .where(
                                                                   userFollow.followed.id.eq(
                                                                                 myUserNo)
                                                                                         .and(
                                                                                             userFollow.delYn.eq(
                                                                                                 YesOrNo.N)))
                                             )));
          return jpaQuery;
        };

    return applyPagination(pageable, jpaQueryFactoryJPAQueryFunction);
  }
}

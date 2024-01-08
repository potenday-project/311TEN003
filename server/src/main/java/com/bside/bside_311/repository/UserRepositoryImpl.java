package com.bside.bside_311.repository;

import com.bside.bside_311.dto.UserIncludeFollowCountDto;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.support.Querydsl4RepositorySupport;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
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

  @Override
  public Page<UserIncludeFollowCountDto> getUsersPopular(Long page, Long size) {
    List<Tuple> userQueryResults = queryFactory.select(user, userFollow.count())
                                               .from(user)
                                               .leftJoin(userFollow)
                                               .on(userFollow.followed.eq(user).and(
                                                   userFollow.delYn.eq(YesOrNo.N)))
                                               .where(user.delYn.eq(YesOrNo.N))
                                               .groupBy(user.id)
                                               .orderBy(userFollow.count().desc())
                                               .offset(page * size)
                                               .limit(size)
                                               .fetch();
    JPAQuery<Integer> prepareCountQuery = queryFactory.selectOne()
                                                      .from(user)
                                                      .leftJoin(userFollow)
                                                      .on(userFollow.followed.eq(user).and(
                                                          userFollow.delYn.eq(YesOrNo.N)))
                                                      .where(user.delYn.eq(YesOrNo.N))
                                                      .groupBy(user.id);
    Page<Tuple> userTuples =
        PageableExecutionUtils.getPage(userQueryResults, PageRequest.of(page.intValue(),
            size.intValue()), () -> prepareCountQuery.fetch().size());
    return userTuples.map(tuple -> {
      return UserIncludeFollowCountDto.of(tuple.get(user), tuple.get(userFollow.count()));
    });
  }
}

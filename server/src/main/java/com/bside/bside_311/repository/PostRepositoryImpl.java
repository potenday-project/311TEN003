package com.bside.bside_311.repository;

import com.bside.bside_311.dto.PostSearchCondition;
import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.support.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;

import static com.bside.bside_311.entity.QAlcohol.alcohol;
import static com.bside.bside_311.entity.QComment.comment;
import static com.bside.bside_311.entity.QPost.post;
import static com.bside.bside_311.entity.QPostAlcohol.postAlcohol;
import static com.bside.bside_311.entity.QPostLike.postLike;
import static com.bside.bside_311.entity.QUser.user;

public class PostRepositoryImpl extends Querydsl4RepositorySupport
    implements PostRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  public PostRepositoryImpl(EntityManager em) {
    super(Post.class);
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Page<Post> searchPageSimple(PostSearchCondition condition, Pageable pageable) {
    //sort 지원.

    Function<JPAQueryFactory, JPAQuery> jpaQueryFactoryJPAQueryFunction =
        query -> {
          JPAQuery<Post> jpaQuery = query.select(post)
                                         .from(post)
                                         .leftJoin(user).on(post.createdBy.eq(user.id).and(
                  user.delYn.eq(YesOrNo.N)));
          if (ObjectUtils.isNotEmpty(condition.getMyUserNo()) &&
                  BooleanUtils.isTrue(condition.getIsLikedByMe())) {
            jpaQuery.innerJoin(postLike).on(postLike.post.eq(post).and(postLike.user.id.eq(
                condition.getMyUserNo())).and(postLike.delYn.eq(YesOrNo.N)));
          }
          return jpaQuery
                     .where(contentLike(condition.getSearchKeyword()),
                         createdByIn(condition.getSearchUserNoList()),
                         isCommentedByMe(condition.getMyUserNo(), condition.getIsCommentedByMe()),
                         notDeleted(),
                         postNosRelatedWithAlcohols(condition.getSearchAlcoholNoList()));
        };
    return applyPagination(pageable, jpaQueryFactoryJPAQueryFunction
    );
  }

  private BooleanExpression contentLike(String searchKeyword) {
    return StringUtils.hasText(searchKeyword) ? post.content.contains(searchKeyword) : null;
  }

  private BooleanExpression createdByIn(List<Long> searchUserNos) {
    return !Collections.isEmpty(searchUserNos) ? post.createdBy.in(searchUserNos) : null;
  }

  private BooleanExpression notDeleted() {
    return post.delYn.eq(YesOrNo.N);
  }

  private BooleanExpression isCommentedByMe(Long myUserNo, Boolean isCommentedByMe) {
    if (ObjectUtils.isNotEmpty(myUserNo) && BooleanUtils.isTrue(isCommentedByMe)) {
      return post.id.in(queryFactory.select(comment.post.id)
                                    .from(comment)
                                    .where(comment.createdBy.eq(myUserNo).and(comment.delYn.eq(
                                        YesOrNo.N)))
                                    .fetch());
    }
    return null;
  }

  private BooleanExpression postNosRelatedWithAlcohols(List<Long> searchALcoholNos) {
    return !Collections.isEmpty(searchALcoholNos) ?
               post.id.in(queryFactory.select(postAlcohol.post.id)
                                      .from(postAlcohol)
                                      .innerJoin(alcohol)
                                      .on(postAlcohol.alcohol.eq(
                                                         alcohol)
                                                             .and(
                                                                 postAlcohol.delYn.eq(
                                                                     YesOrNo.N))
                                                             .and(
                                                                 alcohol.delYn.eq(
                                                                     YesOrNo.N))
                                                             .and(
                                                                 alcohol.id.in(
                                                                     searchALcoholNos)))

                                      .where(
                                          postAlcohol.delYn.eq(
                                              YesOrNo.N).and(
                                              alcohol
                                                  .delYn.eq(
                                                      YesOrNo.N)))
                                      .fetch()) : null;
  }
}

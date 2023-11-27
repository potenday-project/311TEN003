package com.bside.bside_311.repository;

import com.bside.bside_311.dto.PostSearchCondition;
import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.QPost;
import com.bside.bside_311.entity.QUser;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.support.Querydsl4RepositorySupport;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;

import static com.bside.bside_311.entity.QPost.post;

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
        query -> query.select(post)
                     .from(post)
                     .leftJoin(QUser.user).on(post.createdBy.eq(QUser.user.id))
                      .where(contentLike(condition.getSearchKeyword()),
                          createdByIn(condition.getSearchUserNoList()),
                          notDeleted());
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
}

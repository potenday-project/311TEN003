package com.bside.bside_311.repository;

import com.bside.bside_311.dto.AlcoholSearchCondition;
import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.support.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.function.Function;

import static com.bside.bside_311.entity.QAlcohol.alcohol;
import static com.bside.bside_311.entity.QAlcoholType.alcoholType;

public class AlcoholRepositoryImpl extends Querydsl4RepositorySupport
    implements AlcoholRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  public AlcoholRepositoryImpl(EntityManager em) {
    super(Alcohol.class);
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Page<Alcohol> searchAlcoholPage(AlcoholSearchCondition condition, Pageable pageable) {
    Function<JPAQueryFactory, JPAQuery> jpaQueryFactoryJPAQueryFunction =
        query -> query.select(alcohol)
                      .from(alcohol)
                      .leftJoin(alcoholType).on((alcohol.alcoholType.eq(alcoholType))
                                                    .and(alcohol.delYn.eq(YesOrNo.N))
                                                    .and(alcoholType.delYn.eq(YesOrNo.N)))
                      .where(contentLike(condition.getSearchKeyword()),
                          alcoholTypeIs(condition.getAlcoholType()),
                          notDeleted());
    return applyPagination(pageable, jpaQueryFactoryJPAQueryFunction
    );
  }

  private BooleanExpression alcoholTypeIs(Long alcoholType) {
    return ObjectUtils.isNotEmpty(alcoholType) ? alcohol.alcoholType.id.eq(alcoholType) :
               null;
  }

  private BooleanExpression contentLike(String searchKeyword) {
    return StringUtils.hasText(searchKeyword) ? alcohol.name.contains(searchKeyword) : null;
  }

  private BooleanExpression notDeleted() {
    return alcohol.delYn.eq(YesOrNo.N);
  }
}

package com.bside.bside_311.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bside.bside_311.dto.AlcoholSearchCondition;
import com.bside.bside_311.entity.Alcohol;

public interface AlcoholRepositoryCustom {
	Page<Alcohol> searchAlcoholPage(AlcoholSearchCondition condition, Pageable pageable);
}

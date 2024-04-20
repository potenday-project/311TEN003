package com.bside.bside_311.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.YesOrNo;

public interface AlcoholRepository extends JpaRepository<Alcohol, Long>, AlcoholRepositoryCustom {
	Optional<Alcohol> findByNameAndDelYnIs(String name, YesOrNo delYn);

	Optional<Alcohol> findByIdAndDelYnIs(Long userNo, YesOrNo delYn);
}

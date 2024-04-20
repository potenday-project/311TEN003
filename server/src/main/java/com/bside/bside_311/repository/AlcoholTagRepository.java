package com.bside.bside_311.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.AlcoholTag;
import com.bside.bside_311.entity.YesOrNo;

public interface AlcoholTagRepository extends JpaRepository<AlcoholTag, Long> {

	List<AlcoholTag> findByAlcoholAndDelYnIs(Alcohol alcohol, YesOrNo n);
}

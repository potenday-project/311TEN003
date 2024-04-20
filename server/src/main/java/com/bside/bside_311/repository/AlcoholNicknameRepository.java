package com.bside.bside_311.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.AlcoholNickname;
import com.bside.bside_311.entity.YesOrNo;

public interface AlcoholNicknameRepository extends JpaRepository<AlcoholNickname, Long> {
	List<AlcoholNickname> findByAlcoholInAndDelYnIs(List<Alcohol> alcohol, YesOrNo yesOrNo);
}

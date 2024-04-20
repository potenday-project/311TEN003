package com.bside.bside_311.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.bside_311.entity.AlcoholType;
import com.bside.bside_311.entity.YesOrNo;

public interface AlcoholTypeRepository extends JpaRepository<AlcoholType, Long> {
	Optional<AlcoholType> findByIdAndDelYnIs(Long alcoholTypeNo, YesOrNo yesOrNo);

	List<AlcoholType> findByDelYnIsOrderByDisplayOrderAsc(YesOrNo yesOrNo);
}

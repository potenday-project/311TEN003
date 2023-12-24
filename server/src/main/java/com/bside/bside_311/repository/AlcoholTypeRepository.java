package com.bside.bside_311.repository;

import com.bside.bside_311.entity.AlcoholType;
import com.bside.bside_311.entity.YesOrNo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlcoholTypeRepository extends JpaRepository<AlcoholType, Long> {
  Optional<AlcoholType> findByIdAndDelYnIs(Long alcoholTypeNo, YesOrNo yesOrNo);

  List<AlcoholType> findByDelYnIsOrderByDisplayOrderAsc(YesOrNo yesOrNo);
}

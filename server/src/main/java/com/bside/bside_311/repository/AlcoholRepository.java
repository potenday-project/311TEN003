package com.bside.bside_311.repository;

import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.YesOrNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AlcoholRepository extends JpaRepository<Alcohol, Long>, AlcoholRepositoryCustom,
    JpaSpecificationExecutor<Alcohol> {
  Optional<Alcohol> findByNameAndDelYnIs(String name, YesOrNo delYn);

  Optional<Alcohol> findByIdAndDelYnIs(Long userNo, YesOrNo delYn);
}

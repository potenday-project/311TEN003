package com.bside.bside_311.repository;


import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.YesOrNo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
  List<User> findByEmailOrIdAndDelYnIs(String email, String id, YesOrNo delYn);
  Optional<User> findByIdAndDelYnIs(String id, YesOrNo delYn);

  Optional<User> findByUserNoAndDelYnIs(Long userNo, YesOrNo delYn);
}

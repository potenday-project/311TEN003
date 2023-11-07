package com.bside.bside_311.repository;


import com.bside.bside_311.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
}

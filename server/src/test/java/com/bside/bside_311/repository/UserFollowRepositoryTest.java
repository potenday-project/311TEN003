package com.bside.bside_311.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bside.bside_311.custom.annotations.IntegrationTest;
import com.bside.bside_311.entity.UserFollow;
import com.bside.bside_311.entity.YesOrNo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@IntegrationTest
class UserFollowRepositoryTest {
	@Autowired
	private UserFollowRepository userFollowRepository;

	@Test
	void findByFollowing_IdAndDelYnIs() {
		//g
		//w
		List<UserFollow> byFollowing_idAndDelYnIs =
			userFollowRepository.findByFollowing_IdAndDelYnIs(1L, YesOrNo.N);
		//t
		System.out.println("byFollowing_idAndDelYnIs = " + byFollowing_idAndDelYnIs);
	}
}
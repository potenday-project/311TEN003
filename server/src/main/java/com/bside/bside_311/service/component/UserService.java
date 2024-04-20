package com.bside.bside_311.service.component;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.bside.bside_311.dto.UserUpdateRequestDto;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.UserFollowRepository;
import com.bside.bside_311.repository.UserRepository;
import com.bside.bside_311.util.MessageUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final UserFollowRepository userFollowRepository;
	private final PasswordEncoder passwordEncoder;

	public User getUser(Long userNo) {
		if (ObjectUtils.isEmpty(userNo)) {
			return null;
		}
		return userRepository.findByIdAndDelYnIs(userNo, YesOrNo.N).orElseThrow(
			() -> new IllegalArgumentException(MessageUtil.USER_NOT_FOUND_MSG));
	}

	public User getUser(String userId) {
		return userRepository.findByUserIdAndDelYnIs(userId, YesOrNo.N).orElseThrow(
			() -> new IllegalArgumentException(MessageUtil.USER_NOT_FOUND_MSG));
	}

	public User signUp(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return user;
	}

	public void checkDuplicateUser(String email, String userId) {
		List<User> users = searchUsersByEmailOrUserID(email, userId);
		if (users.size() > 0) {
			log.info(">>> UserService.signUp: 중복된 이메일 또는 아이디가 존재합니다.");
			throw new IllegalArgumentException("중복된 이메일 또는 아이디가 존재합니다.");
		}
	}

	public List<User> searchUsersByEmailOrUserID(String email, String userId) {
		return userRepository.findByEmailOrUserIdAndDelYnIs(email, userId,
			YesOrNo.N);
	}

	public List<User> findUsers(List<Long> commentCreatedList) {
		return userRepository.findAllByIdInAndDelYnIs(commentCreatedList, YesOrNo.N);
	}

	public void updateUserWithUserInfo(User user, UserUpdateRequestDto userUpdateRequestDto) {
		if (userUpdateRequestDto != null) {
			if (userUpdateRequestDto.getIntroduction() != null) {
				user.setIntroduction(userUpdateRequestDto.getIntroduction());
			}
			if (userUpdateRequestDto.getNickname() != null) {
				user.setNickname(userUpdateRequestDto.getNickname());
			}
		}
		userRepository.save(user);
	}

	public long getFollowedUserCount(Long userNo) {
		return userFollowRepository.countByFollowedAndDelYnIs(User.of(userNo), YesOrNo.N);
	}

	public long getFollowingUserCount(Long userNo) {
		return userFollowRepository.countByFollowingAndDelYnIs(User.of(userNo), YesOrNo.N);
	}

	public Boolean isFollowing(Long followingNo, Long followedNo) {
		return userFollowRepository.findByFollowingAndFollowedAndDelYnIs(User.of(followingNo),
			User.of(followedNo), YesOrNo.N).isPresent();
	}

}

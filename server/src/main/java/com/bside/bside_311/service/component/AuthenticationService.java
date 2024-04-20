package com.bside.bside_311.service.component;

import static com.bside.bside_311.util.JwtUtil.*;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bside.bside_311.entity.User;
import com.bside.bside_311.repository.UserRepository;
import com.bside.bside_311.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	private final JwtUtil jwtUtil;

	public void checkPasswordValidity(String inputPassword, String existingPassword) {
		if (!passwordEncoder.matches(inputPassword, existingPassword)) {
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		}
	}

	public String makeAccessToken(User foundUser) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(foundUser.getId(), null,
			AuthorityUtils.createAuthorityList(foundUser.getRole().toString()));

		return jwtUtil.createLocalToken(foundUser, NORMAL_TOKEN, normalValidity, authentication);
	}

}

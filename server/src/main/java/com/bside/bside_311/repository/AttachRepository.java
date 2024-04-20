package com.bside.bside_311.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.bside_311.entity.Attach;
import com.bside.bside_311.entity.AttachType;
import com.bside.bside_311.entity.YesOrNo;

public interface AttachRepository extends JpaRepository<Attach, Long> {
	Optional<Attach> findByIdAndDelYnIs(Long attachNo, YesOrNo delYn);

	//  Long<Attach> findByIdAndDelYnIs(Long attachNo, YesOrNo delYn);
	List<Attach> findByRefNoAndAttachTypeIsAndDelYnIs(Long refNo, AttachType attachType,
		YesOrNo delYn);

	List<Attach> findByRefNoInAndAttachTypeIsAndDelYnIs(List<Long> refNo, AttachType attachType,
		YesOrNo delYn);
}

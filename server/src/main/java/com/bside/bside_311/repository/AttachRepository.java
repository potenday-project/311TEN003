package com.bside.bside_311.repository;

import com.bside.bside_311.entity.Attach;
import com.bside.bside_311.entity.AttachType;
import com.bside.bside_311.entity.YesOrNo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttachRepository extends JpaRepository<Attach, Long> {
  Optional<Attach> findByIdAndDelYnIs(Long attachNo, YesOrNo delYn);

  //  Long<Attach> findByIdAndDelYnIs(Long attachNo, YesOrNo delYn);
  List<Attach> findByRefNoAndAttachTypeIsAndDelYnIs(Long refNo, AttachType attachType,
                                                    YesOrNo delYn);

  List<Attach> findByRefNoInAndAttachTypeIsAndDelYnIs(List<Long> refNo, AttachType attachType,
                                                      YesOrNo delYn);
}

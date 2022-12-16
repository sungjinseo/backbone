package dev.greatseo.backbone.domain.member.dao;

import java.util.Optional;

import dev.greatseo.backbone.domain.member.domain.Member;
import dev.greatseo.backbone.domain.member.domain.ReferralCode;
import dev.greatseo.backbone.domain.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberSupportRepository,
    MemberPredicateExecutor<Member> {

  Optional<Member> findByEmail(Email email);

  boolean existsByEmail(Email email);

  boolean existsByReferralCode(ReferralCode referralCode);

}

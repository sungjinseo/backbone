package dev.greatseo.backbone.domain.member.service;

import dev.greatseo.backbone.domain.member.domain.entitiy.Member;
import dev.greatseo.backbone.domain.member.domain.ReferralCode;
import dev.greatseo.backbone.domain.member.domain.repository.MemberRepository;
import dev.greatseo.backbone.domain.member.dto.SignUpRequest;
import dev.greatseo.backbone.domain.member.exception.EmailDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberSignUpService {

    private final MemberRepository memberRepository;

    public Member doSignUp(final SignUpRequest dto) {

        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new EmailDuplicateException(dto.getEmail());
        }

        final ReferralCode referralCode = generateUniqueReferralCode();
        return memberRepository.save(dto.toEntity(referralCode));
    }

    private ReferralCode generateUniqueReferralCode() {
        ReferralCode referralCode;
        do {
            referralCode = ReferralCode.generateCode();
        } while (memberRepository.existsByReferralCode(referralCode));

        return referralCode;
    }

}

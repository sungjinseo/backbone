package dev.greatseo.backbone.domain.member.dao;

import java.util.Optional;

import dev.greatseo.backbone.domain.member.domain.entitiy.Member;
import dev.greatseo.backbone.domain.member.domain.repository.MemberRepository;
import dev.greatseo.backbone.domain.member.exception.EmailNotFoundException;
import dev.greatseo.backbone.domain.member.exception.MemberNotFoundException;
import dev.greatseo.backbone.domain.model.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberFindDao {

    private final MemberRepository memberRepository;

    public Member findById(final Long id) {
        final Optional<Member> member = memberRepository.findById(id);
        member.orElseThrow(() -> new MemberNotFoundException(id));
        return member.get();
    }

    public Member findByEmail(final Email email) {
        final Optional<Member> member = memberRepository.findByEmail(email);
        member.orElseThrow(() -> new EmailNotFoundException(email.getValue()));
        return member.get();
    }

}

package dev.greatseo.backbone.test.setup.domain;


import dev.greatseo.backbone.domain.member.domain.ReferralCode;
import dev.greatseo.backbone.domain.member.domain.entitiy.Member;
import dev.greatseo.backbone.domain.member.domain.repository.MemberRepository;
import dev.greatseo.backbone.domain.model.Email;
import dev.greatseo.backbone.domain.model.Name;
import dev.greatseo.backbone.test.config.TestProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Profile(TestProfile.TEST)
@RequiredArgsConstructor
@Component
public class MemberSetup {

    private final MemberRepository memberRepository;

    public Member save() {
        final Member member = buildMember("yun@test.com");
        return memberRepository.save(member);
    }

    public List<Member> save(int count) {
        List<Member> members = new ArrayList<>();
        IntStream.range(0, count).forEach(i -> members.add(
                memberRepository.save(buildMember(String.format("test00%d@test.com", i))))
        );
        return members;
    }

    public Member build(){
        return buildMember("yun@test.com");
    }

    private Member buildMember(String email) {
        return Member.builder()
                .email(Email.of(email))
                .name(Name.builder()
                        .first("first")
                        .middle("middle")
                        .last("last")
                        .build())
                .referralCode(ReferralCode.generateCode())
                .build();
    }

}

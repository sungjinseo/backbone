package dev.greatseo.backbone.domain.member.domain;

import dev.greatseo.backbone.domain.member.domain.entitiy.Member;
import dev.greatseo.backbone.domain.model.Email;
import dev.greatseo.backbone.domain.model.Name;


public class MemberBuilder {

    public static Member build() {
        final String value = "cheese10yun@gmail.com";
        final Email email = Email.of(value);
        final Name name = nameBuild();
        return createMember(email, name);
    }

    public static Member build(Email email, Name name) {
        return createMember(email, name);
    }

    private static Member createMember(Email email, Name name) {
        return Member.builder()
                .email(email)
                .name(name)
                .referralCode(ReferralCode.generateCode())
                .build();
    }

    private static Name nameBuild() {
        return Name.builder()
                .first("first")
                .middle("middle")
                .last("last")
                .build();
    }


}
package dev.greatseo.backbone.domain.member.dto;

import dev.greatseo.backbone.domain.member.domain.entitiy.Member;
import dev.greatseo.backbone.domain.member.domain.ReferralCode;
import dev.greatseo.backbone.domain.model.Email;
import dev.greatseo.backbone.domain.model.Name;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SignUpRequest {

    @Valid
    private Email email;

    @Valid
    private Name name;

    SignUpRequest(@Valid Email email, @Valid Name name) {
        this.email = email;
        this.name = name;
    }

    public Member toEntity(final ReferralCode referralCode) {
        return Member.builder()
                .name(name)
                .email(email)
                .referralCode(referralCode)
                .build();
    }
}

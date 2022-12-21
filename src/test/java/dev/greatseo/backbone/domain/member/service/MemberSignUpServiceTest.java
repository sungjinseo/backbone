package dev.greatseo.backbone.domain.member.service;

import dev.greatseo.backbone.domain.member.domain.MemberBuilder;
import dev.greatseo.backbone.domain.member.domain.entitiy.Member;
import dev.greatseo.backbone.domain.member.domain.repository.MemberRepository;
import dev.greatseo.backbone.domain.member.dto.SignUpRequest;
import dev.greatseo.backbone.domain.member.dto.SignUpRequestBuilder;
import dev.greatseo.backbone.domain.member.exception.EmailDuplicateException;
import dev.greatseo.backbone.domain.model.Email;
import dev.greatseo.backbone.domain.model.Name;
import dev.greatseo.backbone.test.MockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


public class MemberSignUpServiceTest extends MockTest {

    @InjectMocks
    private MemberSignUpService memberSignUpService;

    @Mock
    private MemberRepository memberRepository;
    private Member member;

    @BeforeEach
    public void setUp() throws Exception {
        member = MemberBuilder.build();
    }

    @Test
    public void 회원가입_성공() {
        //given
        final Email email = member.getEmail();
        final Name name = member.getName();
        final SignUpRequest dto = SignUpRequestBuilder.build(email, name);

        given(memberRepository.existsByEmail(any())).willReturn(false);
        given(memberRepository.save(any())).willReturn(member);

        //when
        final Member signUpMember = memberSignUpService.doSignUp(dto);

        //then
        assertThat(signUpMember).isNotNull();
        assertThat(signUpMember.getEmail().getValue()).isEqualTo(member.getEmail().getValue());
        assertThat(signUpMember.getName().getFullName()).isEqualTo(member.getName().getFullName());
    }

    @Test
    public void 회원가입_이메일중복_경우() {
        //given
        final Email email = member.getEmail();
        final Name name = member.getName();
        final SignUpRequest dto = SignUpRequestBuilder.build(email, name);

        given(memberRepository.existsByEmail(any())).willReturn(true);

        //when
        assertThrows(EmailDuplicateException.class, () -> {
            memberSignUpService.doSignUp(dto);
        });

    }
}
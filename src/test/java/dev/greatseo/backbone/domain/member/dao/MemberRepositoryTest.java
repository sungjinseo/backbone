package dev.greatseo.backbone.domain.member.dao;


import com.querydsl.core.types.Predicate;
import dev.greatseo.backbone.domain.member.domain.MemberBuilder;
import dev.greatseo.backbone.domain.member.domain.entitiy.Member;
import dev.greatseo.backbone.domain.member.domain.entitiy.QMember;
import dev.greatseo.backbone.domain.member.domain.repository.MemberRepository;
import dev.greatseo.backbone.domain.model.Email;
import dev.greatseo.backbone.domain.model.Name;
import dev.greatseo.backbone.test.RepositoryTest;
import dev.greatseo.backbone.test.setup.model.EmailBuilder;
import dev.greatseo.backbone.test.setup.model.NameBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class MemberRepositoryTest extends RepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member saveMember;
    private Email email;

    @BeforeEach
    public void setUp() throws Exception {
        final String value = "cheese10yun@gmail.com";
        email = EmailBuilder.build(value);
        final Name name = NameBuilder.build();
        saveMember = memberRepository.save(MemberBuilder.build(email, name));
    }

    @Test
    public void 회원객채생성() {
        assertThat(saveMember.getEmail().getValue()).isEqualTo(email.getValue());
        assertThat(saveMember.getName().getFirst()).isEqualTo("first");
        assertThat(saveMember.getName().getMiddle()).isEqualTo("middle");
        assertThat(saveMember.getName().getLast()).isEqualTo("last");
        assertThat(saveMember.getCreateAt())
            .isGreaterThanOrEqualTo(LocalDateTime.now().minusMinutes(1));
        assertThat(saveMember.getUpdateAt())
            .isGreaterThanOrEqualTo(LocalDateTime.now().minusMinutes(1));
    }

    @Test
    public void findByEmailTest() {
        final Optional<Member> byEmail = memberRepository.findByEmail(email);
        final Member member = byEmail.get();
        assertThat(member.getEmail().getValue()).isEqualTo(email.getValue());
    }

    @Test
    public void existsByEmail_존재하는경우_true() {
        final boolean existsByEmail = memberRepository.existsByEmail(email);
        assertThat(existsByEmail).isTrue();
    }

    @Test
    public void existsByEmail_존재하지않은_경우_false() {
        final boolean existsByEmail = memberRepository.existsByEmail(Email.of("ehdgoanfrhkqortntksdls@asd.com"));
        assertThat(existsByEmail).isFalse();
    }

    @Test
    public void predicate_test() {
        //given
        final QMember qMember = QMember.member;
        final Predicate predicate = qMember.email.eq(Email.of("cheese10yun@gmail.com"));

        //when
        final boolean exists = memberRepository.exists(predicate);

        //then
        assertThat(exists).isTrue();
    }

    @Test
    public void searchByEmail_test() {
        final List<Member> members = memberRepository.searchByEmail(email);
        assertThat(members).contains(saveMember);
        assertThat(members.size()).isGreaterThanOrEqualTo(1);
    }
}
package dev.greatseo.backbone.domain.member.dao;

import dev.greatseo.backbone.domain.member.domain.MemberBuilder;
import dev.greatseo.backbone.domain.member.domain.entitiy.Member;
import dev.greatseo.backbone.domain.member.domain.repository.MemberRepository;
import dev.greatseo.backbone.domain.member.exception.EmailNotFoundException;
import dev.greatseo.backbone.domain.member.exception.MemberNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


public class MemberFindDaoTest {

    private Member member;

    @BeforeEach
    public void setUp() throws Exception {
        member = MemberBuilder.build();
    }

    @InjectMocks
    private MemberFindDao memberFindDao;

    @Mock
    private MemberRepository memberRepository;

    @Test
    public void findById_존재하는_경우() {
        //given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        //when
        final Member member = memberFindDao.findById(anyLong());

        //then
        assertThat(member).isNotNull();
    }

    @Test
    public void findById_존재하지않은_경우() {
        //given
        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        assertThrows(MemberNotFoundException.class, () -> {
            memberFindDao.findById(anyLong());
        });
    }

    @Test
    public void findBy_Email_존재하는_경우() {
        //given
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));

        //when
        final Member member = memberFindDao.findByEmail(any());

        //then
        assertThat(member).isNotNull();
    }

    @Test
    public void findBy_Email_존재하지않은_경우() {
        //given
        given(memberRepository.findByEmail(any())).willReturn(Optional.empty());

        //when
        assertThrows(EmailNotFoundException.class, () -> {
            memberFindDao.findByEmail(member.getEmail());
        });


    }

    @Test
    public void try_test() {

        try {
            // 비지니스 로직 수행...
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
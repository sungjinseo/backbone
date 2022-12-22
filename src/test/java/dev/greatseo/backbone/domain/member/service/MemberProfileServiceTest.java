package dev.greatseo.backbone.domain.member.service;

import dev.greatseo.backbone.domain.member.dao.MemberFindDao;
import dev.greatseo.backbone.domain.member.domain.MemberBuilder;
import dev.greatseo.backbone.domain.member.domain.entitiy.Member;
import dev.greatseo.backbone.domain.member.dto.MemberProfileUpdate;
import dev.greatseo.backbone.test.ServiceTest;
import dev.greatseo.backbone.test.setup.request.MemberProfileUpdateBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

public class MemberProfileServiceTest extends ServiceTest {

    @InjectMocks
    private MemberProfileService memberProfileService;

    @Mock
    private MemberFindDao memberFindDao;

    @Test
    public void updateTest() {
        //given
        final Member member = MemberBuilder.build();
        final MemberProfileUpdate dto = MemberProfileUpdateBuilder.build();
        given(memberFindDao.findById(anyLong())).willReturn(member);

        //when
        final Member updateMember = memberProfileService.update(anyLong(), dto);


        //then
        assertThat(updateMember.getName().getFirst()).isEqualTo(dto.getName().getFirst());
        assertThat(updateMember.getName().getFullName()).isEqualTo(dto.getName().getFullName());
        assertThat(updateMember.getName().getMiddle()).isEqualTo(dto.getName().getMiddle());
        assertThat(updateMember.getName().getLast()).isEqualTo(dto.getName().getLast());
    }
}
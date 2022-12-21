package dev.greatseo.backbone.domain.member.controller;

import dev.greatseo.backbone.domain.member.domain.MemberBuilder;
import dev.greatseo.backbone.domain.member.domain.ReferralCode;
import dev.greatseo.backbone.domain.member.domain.entitiy.Member;
import dev.greatseo.backbone.domain.member.dto.MemberExistenceType;
import dev.greatseo.backbone.domain.member.dto.MemberProfileUpdate;
import dev.greatseo.backbone.domain.member.dto.SignUpRequest;
import dev.greatseo.backbone.domain.member.dto.SignUpRequestBuilder;
import dev.greatseo.backbone.domain.model.Email;
import dev.greatseo.backbone.domain.model.Name;
import dev.greatseo.backbone.global.error.exception.ErrorCode;
import dev.greatseo.backbone.test.IntegrationTest;
import dev.greatseo.backbone.test.setup.domain.MemberSetup;
import dev.greatseo.backbone.test.setup.request.MemberProfileUpdateBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberApiTest extends IntegrationTest {

    @Autowired
    private MemberSetup memberSetup;

    //@Test
    public void 회원가입_성공() throws Exception {
        //given
        final Member member = MemberBuilder.build();
        final Email email = member.getEmail();
        final Name name = member.getName();
        final SignUpRequest dto = SignUpRequestBuilder.build(email, name);

        //when
        final ResultActions resultActions = requestSignUp(dto);

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("email.value").value(email.getValue()))
                .andExpect(jsonPath("email.host").value(email.getHost()))
                .andExpect(jsonPath("email.id").value(email.getId()))
                .andExpect(jsonPath("name.first").value(name.getFirst()))
                .andExpect(jsonPath("name.middle").value(name.getMiddle()))
                .andExpect(jsonPath("name.last").value(name.getLast()))
                .andExpect(jsonPath("name.fullName").value(name.getFullName()))
        ;
    }

    @Test
    public void 회원가입_유효하지않은_입력값() throws Exception {
        //given
        final Email email = Email.of("asdasd@dasd.com");
        final Name name = Name.builder().build();

        final SignUpRequest dto = SignUpRequestBuilder.build(email, name);

        //when
        final ResultActions resultActions = requestSignUp(dto);

        //then
        resultActions
                .andExpect(status().isBadRequest())
        ;

    }

    @Test
    public void 회원조회() throws Exception {
        //given
        final Member member = memberSetup.save();

        //when
        final ResultActions resultActions = requestGetMember(member.getId());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("email.value").value(member.getEmail().getValue()))
                .andExpect(jsonPath("email.host").value(member.getEmail().getHost()))
                .andExpect(jsonPath("email.id").value(member.getEmail().getId()))
                .andExpect(jsonPath("name.first").value(member.getName().getFirst()))
                .andExpect(jsonPath("name.middle").value(member.getName().getMiddle()))
                .andExpect(jsonPath("name.last").value(member.getName().getLast()))
                .andExpect(jsonPath("name.fullName").value(member.getName().getFullName()))
        ;
    }

    @Test
    public void 회원조회_회원이없는경우() throws Exception {
        //given

        //when
        final ResultActions resultActions = requestGetMember(0L);

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(ErrorCode.ENTITY_NOT_FOUND.getMessage()))
                .andExpect(jsonPath("status").value(ErrorCode.ENTITY_NOT_FOUND.getStatus()))
                .andExpect(jsonPath("code").value(ErrorCode.ENTITY_NOT_FOUND.getCode()))
                .andExpect(jsonPath("errors").isEmpty())
        ;

    }

    @Test
    public void 회원_프로필_업데이트() throws Exception {
        //given
        final Member member = memberSetup.save();
        final MemberProfileUpdate dto = MemberProfileUpdateBuilder.build();

        //when

        final ResultActions resultActions = requestUpdateProfile(dto, member.getId());

        //then
        resultActions
                .andExpect(status().isOk());

    }

    @Test
    public void 이메일_존재_있을_경우() throws Exception {
        //given
        final MemberExistenceType type = MemberExistenceType.EMAIL;
        final Member member = memberSetup.save();
        final String email = member.getEmail().getValue();

        //when
        final ResultActions resultActions = requestExistenceTarget(type, email);

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("existence").value(true));

    }

    @Test
    public void 이메일_존재_없을_경우() throws Exception {
        //given
        final MemberExistenceType type = MemberExistenceType.EMAIL;
        final String email = Email.of("asdasdaasd@asdasdasd.com").getValue();

        //when
        final ResultActions resultActions = requestExistenceTarget(type, email);

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("existence").value(false));
    }

    @Test
    public void 추천인_존재_있을_경우() throws Exception {
        //given
        final MemberExistenceType type = MemberExistenceType.REFERRAL_CODE;
        final Member member = memberSetup.save();
        final String code = member.getReferralCode().getValue();

        //when
        final ResultActions resultActions = requestExistenceTarget(type, code);

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("existence").value(true));

    }

    @Test
    public void 추천인_존재_없을_경우() throws Exception {
        //given
        final MemberExistenceType type = MemberExistenceType.REFERRAL_CODE;
        final String code = ReferralCode.of("123123").getValue();

        //when
        final ResultActions resultActions = requestExistenceTarget(type, code);

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("existence").value(false));
    }

    private ResultActions requestExistenceTarget(MemberExistenceType type, String value) throws Exception {
        return mvc.perform(get("/api/v1/members/existence")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("type", type.name())
                .param("value", value))
                .andDo(print());
    }

    private ResultActions requestUpdateProfile(final MemberProfileUpdate dto, final long id) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.put("/api/v1/members/{id}/profile", id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }

    private ResultActions requestGetMember(long memberId) throws Exception {
        return mvc.perform(get("/api/v1/members/{id}", memberId)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }

    private ResultActions requestSignUp(SignUpRequest dto) throws Exception {
        return mvc.perform(post("/api/v1/members")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }
}
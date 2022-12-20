package dev.greatseo.backbone.domain.member.controller;

import dev.greatseo.backbone.domain.member.dao.MemberFindDao;
import dev.greatseo.backbone.domain.member.domain.MemberBuilder;
import dev.greatseo.backbone.domain.member.domain.entitiy.Member;
import dev.greatseo.backbone.domain.member.dto.SignUpRequest;
import dev.greatseo.backbone.domain.member.dto.SignUpRequestBuilder;
import dev.greatseo.backbone.domain.member.service.MemberProfileService;
import dev.greatseo.backbone.domain.member.service.MemberSearchService;
import dev.greatseo.backbone.domain.member.service.MemberSignUpService;
import dev.greatseo.backbone.domain.model.Email;
import dev.greatseo.backbone.domain.model.Name;
import dev.greatseo.backbone.test.MockApiTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs
@WebMvcTest(MemberController.class)
public class MemberMockApiTest extends MockApiTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    MockMvc mvc;

    @MockBean
    private MemberSignUpService memberSignUpService;

    @MockBean
    private MemberFindDao memberFindDao;

    @MockBean
    private MemberProfileService memberProfileService;

    @MockBean
    private MemberSearchService memberSearchService;


//    @BeforeAll
//    public void setUp()
//    {
//        mvc = buildMockMvc(context);
//    }

    @Test
    public void 회원가입_성공() throws Exception {
        //given
        final Member member = MemberBuilder.build();
        final Email email = member.getEmail();
        final Name name = member.getName();
        final SignUpRequest dto = SignUpRequestBuilder.build(email, name);

        given(memberSignUpService.doSignUp(any())).willReturn(member);

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

    //@Test
    public void 회원가입_유효하지않은_입력값() throws Exception {
        //given
        final Email email = Email.of("asdasd@d");
        final Name name = Name.builder().build();
        final SignUpRequest dto = SignUpRequestBuilder.build(email, name);
        final Member member = MemberBuilder.build();

        given(memberSignUpService.doSignUp(any())).willReturn(member);

        //when
        final ResultActions resultActions = requestSignUp(dto);

        //then
        resultActions
                .andExpect(status().isBadRequest())
        ;

    }

    private ResultActions requestSignUp(SignUpRequest dto) throws Exception {
        return mvc.perform(post("api/v1/members")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }
}
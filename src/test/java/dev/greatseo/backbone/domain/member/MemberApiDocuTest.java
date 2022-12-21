package dev.greatseo.backbone.domain.member;

import dev.greatseo.backbone.domain.member.domain.MemberBuilder;
import dev.greatseo.backbone.domain.member.domain.entitiy.Member;
import dev.greatseo.backbone.domain.member.dto.SignUpRequest;
import dev.greatseo.backbone.domain.member.dto.SignUpRequestBuilder;
import dev.greatseo.backbone.domain.model.Email;
import dev.greatseo.backbone.domain.model.Name;
import dev.greatseo.backbone.test.MockControllerTest;
import net.minidev.json.JSONObject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;


class MemberApiDocuTest extends MockControllerTest {

    private static final Snippet REQUEST_FIELDS = requestFields(
            fieldWithPath("email").type(JsonFieldType.OBJECT).description("이메일정보"),
            fieldWithPath("email.value").type(JsonFieldType.STRING).description("이메일주소"),
            fieldWithPath("email.id").type(JsonFieldType.STRING).description("이메일아이디"),
            fieldWithPath("email.host").type(JsonFieldType.STRING).description("호스트주소"),
            fieldWithPath("name").type(JsonFieldType.OBJECT).description("이름정보"),
            fieldWithPath("name.fullName").type(JsonFieldType.STRING).description("풀네임"),
            fieldWithPath("name.first").type(JsonFieldType.STRING).description("이름"),
            fieldWithPath("name.middle").type(JsonFieldType.STRING).description("중간이름"),
            fieldWithPath("name.last").type(JsonFieldType.STRING).description("성")
    );

    private static final Snippet RESPONSE_FIELDS = responseFields(
            fieldWithPath("email").type(JsonFieldType.OBJECT).description("이메일정보"),
            fieldWithPath("email.value").type(JsonFieldType.STRING).description("이메일주소"),
            fieldWithPath("email.id").type(JsonFieldType.STRING).description("이메일아이디"),
            fieldWithPath("email.host").type(JsonFieldType.STRING).description("호스트주소"),
            fieldWithPath("name").type(JsonFieldType.OBJECT).description("이름정보"),
            fieldWithPath("name.fullName").type(JsonFieldType.STRING).description("풀네임"),
            fieldWithPath("name.first").type(JsonFieldType.STRING).description("이름"),
            fieldWithPath("name.middle").type(JsonFieldType.STRING).description("중간이름"),
            fieldWithPath("name.last").type(JsonFieldType.STRING).description("성")
    );

    @Test
    void users_success_test() {


        final Member member = MemberBuilder.build();
        final Email email = member.getEmail();
        final Name name = member.getName();
        final SignUpRequest dto = SignUpRequestBuilder.build(email, name);

        String expectedEmail = "cheese10yun@gmail.com";
        //String expectedName = "";
        //int expectedAge = 27;

        JSONObject requestBody = new JSONObject();
        requestBody.put("email", email);
        requestBody.put("name", name);

        given(this.spec)
                .filter(document(DEFAULT_RESTDOC_PATH, REQUEST_FIELDS, RESPONSE_FIELDS))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Content-type", "application/json")
                .body(requestBody)
                .log().all()

                .when()
                    .post("/api/v1/members")


                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("email.value", Matchers.equalTo(expectedEmail));
    }
}
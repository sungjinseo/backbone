package dev.greatseo.backbone.domain.member.controller;

import javax.validation.Valid;

import dev.greatseo.backbone.domain.member.dto.SignInReqDto;
import dev.greatseo.backbone.domain.member.dto.SignInResDto;
import dev.greatseo.backbone.domain.member.dto.SignUpReqDto;
import dev.greatseo.backbone.domain.member.service.SignService;
import dev.greatseo.backbone.global.util.auth.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/v1"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class SignController {

    private final SignService apiSignService;

    private final AuthProvider authProvider;

    /**
     * @method 설명 : 회원가입
     * @param signupDto
     * @throws Exception
     */
    @PostMapping(value = {"/signup"})
    public ResponseEntity<Boolean> appSignUp(@Valid @RequestBody SignUpReqDto signupDto) throws Exception {

        return ResponseEntity.ok()
                .body(apiSignService.signupMember(joinDto));
    }

    /**
     * @method 설명 : 로그인
     * @param signinReqDto
     * @throws Exception
     */
    @PostMapping(value = {"/signin"})
    public ResponseEntity<SignInReqDto> appSignIn(@Valid @RequestBody SignInReqDto signinReqDto) throws Exception {

        SignInResDto authentication = apiSignService.signinMember(signinReqDto);

        return ResponseEntity.ok()
                .header("accesstoken", authProvider
                        .createToken(
                                authentication.getId(),
                                authentication.getEmail(),
                                "USER"))
                .body(authentication);
    }
}

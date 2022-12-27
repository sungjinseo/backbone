package dev.greatseo.backbone.domain.member.service;

import dev.greatseo.backbone.domain.member.dto.SignInReqDto;
import dev.greatseo.backbone.domain.member.dto.SignInResDto;
import dev.greatseo.backbone.domain.member.dto.SignUpReqDto;
import dev.greatseo.portfolio.api.sign.dto.AuthenticationDto;
import dev.greatseo.portfolio.api.sign.dto.JoinDto;
import dev.greatseo.portfolio.api.sign.dto.LoginDto;

public interface SignService {

	public Boolean signupMember(SignUpReqDto joinDto);
	public SignInResDto signinMember(SignInReqDto loginDto);

}

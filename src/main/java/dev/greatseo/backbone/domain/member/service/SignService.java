package dev.greatseo.backbone.domain.member.service;

import dev.greatseo.backbone.domain.member.dto.SignInDto;
import dev.greatseo.backbone.domain.member.dto.SignUpDto;
import dev.greatseo.portfolio.api.sign.dto.AuthenticationDto;
import dev.greatseo.portfolio.api.sign.dto.JoinDto;
import dev.greatseo.portfolio.api.sign.dto.LoginDto;

public interface SignService {

	public Boolean regMember(SignUpDto joinDto);
	public AuthenticationDto loginMember(SignInDto loginDto);

}

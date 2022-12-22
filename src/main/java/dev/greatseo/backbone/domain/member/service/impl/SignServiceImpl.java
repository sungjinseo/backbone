package dev.greatseo.backbone.domain.member.service.impl;

import dev.greatseo.backbone.domain.member.domain.repository.MemberRepository;
import dev.greatseo.backbone.domain.member.service.SignService;
import dev.greatseo.portfolio.api.member.domain.entity.Members;
import dev.greatseo.portfolio.api.member.domain.repository.MemberRepository;
import dev.greatseo.portfolio.api.sign.dto.AuthenticationDto;
import dev.greatseo.portfolio.api.sign.dto.JoinDto;
import dev.greatseo.portfolio.api.sign.dto.LoginDto;
import dev.greatseo.portfolio.api.sign.service.SignService;
import dev.greatseo.portfolio.exception.custom.DuplicatedException;
import dev.greatseo.portfolio.exception.custom.ForbiddenException;
import dev.greatseo.portfolio.exception.custom.UserNotFoundException;
import dev.greatseo.portfolio.util.validation.Empty;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("signService")
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    public Boolean regMember(JoinDto joinDto) {

        // 아이디 중복체크
        if (!Empty.validation(memberRepository.countByEmail(joinDto.getEmail())))
            throw new DuplicatedException("Duplicated ID");

        // 연락처 중복체크
        if (!Empty.validation(memberRepository.countByMobile(joinDto.getMobile())))
            throw new DuplicatedException("Duplicated Mobile");

        // 비밀번호 암호화처리
        joinDto.setPassword(passwordEncoder.encode(joinDto.getPassword()));

        // 데이터 등록(저장)
        memberRepository.save(joinDto.toEntity());

        return true;
    }

    public AuthenticationDto loginMember(LoginDto loginDto) {

        // dto -> entity
        Members loginEntity = loginDto.toEntity();

        // 회원 엔티티 객체 생성 및 조회시작
        Members member = memberRepository.findByEmail(loginEntity.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (!passwordEncoder.matches(loginEntity.getPassword(), member.getPassword()))
            throw new ForbiddenException("Passwords do not match");

        // 회원정보를 인증클래스 객체(authentication)로 매핑

        return modelMapper.map(member, AuthenticationDto.class);
    }
}

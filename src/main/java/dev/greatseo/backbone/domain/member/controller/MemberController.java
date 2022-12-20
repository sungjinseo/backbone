package dev.greatseo.backbone.domain.member.controller;

import dev.greatseo.backbone.domain.member.dao.MemberFindDao;
import dev.greatseo.backbone.domain.member.domain.entitiy.Member;
import dev.greatseo.backbone.domain.member.dto.MemberExistenceType;
import dev.greatseo.backbone.domain.member.dto.MemberProfileUpdate;
import dev.greatseo.backbone.domain.member.dto.MemberResponse;
import dev.greatseo.backbone.domain.member.dto.SignUpRequest;
import dev.greatseo.backbone.domain.member.service.MemberProfileService;
import dev.greatseo.backbone.domain.member.service.MemberSearchService;
import dev.greatseo.backbone.domain.member.service.MemberSignUpService;
import dev.greatseo.backbone.global.response.Existence;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final MemberSignUpService memberSignUpService;
    private final MemberFindDao memberFindDao;
    private final MemberProfileService memberProfileService;
    private final MemberSearchService memberSearchService;

    @PostMapping("/members")
    public MemberResponse create(@RequestBody @Valid final SignUpRequest dto) {
        final Member member = memberSignUpService.doSignUp(dto);
        return new MemberResponse(member);
    }

    @GetMapping("/members/{id}")
    public MemberResponse getMember(@PathVariable long id) {
        return new MemberResponse(memberFindDao.findById(id));
    }

    @PutMapping("/members/{id}/profile")
    public void updateProfile(@PathVariable long id, @RequestBody @Valid final MemberProfileUpdate dto) {
        memberProfileService.update(id, dto);
    }

    @GetMapping("/members/existence")
    public Existence isExistTarget(
            @RequestParam("type") final MemberExistenceType type,
            @RequestParam(value = "value", required = false) final String value
    ) {
        return new Existence(memberSearchService.isExistTarget(type, value));
    }

}

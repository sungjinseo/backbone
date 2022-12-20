package dev.greatseo.backbone.domain.member.service;

import dev.greatseo.backbone.domain.member.dao.MemberFindDao;
import dev.greatseo.backbone.domain.member.domain.entitiy.Member;
import dev.greatseo.backbone.domain.member.dto.MemberProfileUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberProfileService {

  private final MemberFindDao memberFindDao;

    public Member update(final long memberId, final MemberProfileUpdate dto) {
      final Member member = memberFindDao.findById(memberId);
        member.updateProfile(dto.getName());
        return member;
    }

}

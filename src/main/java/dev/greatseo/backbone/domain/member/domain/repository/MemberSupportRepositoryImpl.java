package dev.greatseo.backbone.domain.member.domain.repository;

import dev.greatseo.backbone.domain.member.domain.entitiy.Member;
import dev.greatseo.backbone.domain.member.domain.entitiy.QMember;
import dev.greatseo.backbone.domain.model.Email;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public class MemberSupportRepositoryImpl extends QuerydslRepositorySupport implements
    MemberSupportRepository {

  public MemberSupportRepositoryImpl() {
    super(Member.class);
  }

  @Override
  public List<Member> searchByEmail(final Email email) {

    final QMember qMember = QMember.member;

    return from(qMember)
        .where(qMember.email.value.like(email.getValue() + "%"))
        .fetch();
  }

}

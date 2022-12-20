package dev.greatseo.backbone.domain.member.domain.repository;

import dev.greatseo.backbone.domain.member.domain.entitiy.Member;
import dev.greatseo.backbone.domain.model.Email;

import java.util.List;

public interface MemberSupportRepository {

  List<Member> searchByEmail(Email email);

}

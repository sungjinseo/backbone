package dev.greatseo.backbone.domain.member.dao;

import dev.greatseo.backbone.domain.member.domain.Member;
import dev.greatseo.backbone.domain.model.Email;

import java.util.List;

public interface MemberSupportRepository {

  List<Member> searchByEmail(Email email);
}

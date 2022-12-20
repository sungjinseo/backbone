package dev.greatseo.backbone.domain.member.dto;

import dev.greatseo.backbone.domain.model.Email;
import dev.greatseo.backbone.domain.model.Name;

public class SignUpRequestBuilder {

  public static SignUpRequest build(Email email, Name name) {
    return new SignUpRequest(email, name);
  }


}
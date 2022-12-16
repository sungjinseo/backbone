package dev.greatseo.backbone.domain.member.exception;

import dev.greatseo.backbone.global.error.exception.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {

    public MemberNotFoundException(Long target) {
        super(target + " is not found");
    }
}

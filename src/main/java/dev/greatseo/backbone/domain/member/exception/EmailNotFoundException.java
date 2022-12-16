package dev.greatseo.backbone.domain.member.exception;


import dev.greatseo.backbone.global.error.exception.EntityNotFoundException;

public class EmailNotFoundException extends EntityNotFoundException {

    public EmailNotFoundException(String target) {
        super(target + " is not found");
    }
}

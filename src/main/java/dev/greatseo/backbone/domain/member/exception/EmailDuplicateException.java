package dev.greatseo.backbone.domain.member.exception;


import dev.greatseo.backbone.domain.model.Email;
import dev.greatseo.backbone.global.error.exception.ErrorCode;
import dev.greatseo.backbone.global.error.exception.InvalidValueException;

public class EmailDuplicateException extends InvalidValueException {

    public EmailDuplicateException(final Email email) {
        super(email.getValue(), ErrorCode.EMAIL_DUPLICATION);
    }
}

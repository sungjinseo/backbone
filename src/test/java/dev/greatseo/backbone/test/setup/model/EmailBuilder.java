package dev.greatseo.backbone.test.setup.model;

import dev.greatseo.backbone.domain.model.Email;

public class EmailBuilder {

    public static Email build() {
        return Email.of("yun@test.com");
    }

    public static Email build(final String email) {
        return Email.of(email);
    }


}

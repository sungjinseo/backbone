package dev.greatseo.backbone.test.setup.request;

import dev.greatseo.backbone.domain.member.dto.MemberProfileUpdate;
import dev.greatseo.backbone.domain.model.Name;
import dev.greatseo.backbone.test.setup.model.NameBuilder;


public class MemberProfileUpdateBuilder {

    public static MemberProfileUpdate build() {
        final Name name = NameBuilder.build("qqwew", "asdxca", "adwwd");
        return new MemberProfileUpdate(name);
    }
}

package dev.greatseo.backbone.domain.member.dto;


import dev.greatseo.portfolio.api.member.domain.entity.Members;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class SignUpDto {

    @NotBlank(message = "'email' is a required input value")
    @Email(message = "do not email type")
    private String email;

    @NotBlank(message = "'password' is a required input value")
    private String password;

    @NotBlank(message = "'name' is a required input value")
    private String name;

    @NotBlank(message = "'nickname' is a required input value")
    private String nickname;

    @NotBlank(message = "'mobile' is a required input value")
    private String mobile;

    public Members toEntity() {

        Members build = Members.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .mobile(mobile)
                .build();

        return build;
    }

}

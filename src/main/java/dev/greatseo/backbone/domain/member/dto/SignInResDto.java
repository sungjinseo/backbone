package dev.greatseo.backbone.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignInResDto {
    private Long id;

    private String email;

    private String name;

    private String nickname;

    private String mobile;

    private String regDate;

    private String modDate;

}

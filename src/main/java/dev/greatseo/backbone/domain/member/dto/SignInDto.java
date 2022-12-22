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
public class SignInDto {

	@NotBlank(message = "'email' is a required input value")
	@Email(message = "It is not in email format")
	private String email;

	@NotBlank(message = "'password' is a required input value")
	private String password;

	
	public Members toEntity() {
		Members build = Members.builder()
				.email(email)
				.password(password)
				.build();
		
		return build;
	}
	
}

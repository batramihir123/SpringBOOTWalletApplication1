package com.HelloSpring.dto.request;

import com.HelloSpring.model.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoginDTO {
	
	@NotBlank(message = "emailId name should not be blank")
	@NotNull(message="emailId name should not be null")
	@Email(message ="not valid email formatrrrrrrrrrrrrrr ")
	private String emailId;
	
	
	 @NotBlank(message = "password name should not be blank")
	 @NotNull(message="password name should not be null")
	 @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
	 private String  password;
}

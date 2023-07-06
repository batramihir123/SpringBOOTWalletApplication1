package com.HelloSpring.dto.request;



import com.HelloSpring.model.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto {
	
	@NotBlank(message = "First name should not be blank")
	@NotNull(message="First name should not be null")
	@Size(min = 2,max = 60,message = "Name should be between 2 to 60")
	private String firstName;
	@NotBlank(message = "Last name should not be blank")
	@NotNull(message="Last name should not be null")
	private String lastName;
	@NotBlank(message = "emailId name should not be blank")
	@NotNull(message="emailId name should not be null")
	@Email(message ="not valid email formatrrrrrrrrrrrrrr ")
	private String emailId;
	 @NotBlank(message = "contactNo name should not be blank")
	 @NotNull(message="contactNo name should not be null")
	private String contactNo;
	private Gender gender;
	 @NotBlank(message = "password name should not be blank")
	 @NotNull(message="password name should not be null")
	 @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
	 private String  password;
	 @NotBlank(message = "confirmPassword name should not be blank")
	 @NotNull(message="confirmPassword name should not be null")
	 private String confirmPassword;
	 @NotBlank(message = "addressLine1 name should not be blank")
	 @NotNull(message="addressLine1 name should not be null")
	 private String addressLine1;
	 @NotBlank(message = "addressLine2 name should not be blank")
	 @NotNull(message="addressLine2 name should not be null")
	 private String addressLine2;
	 @NotBlank(message ="City name should not be blank")
	 @NotNull(message="City name should not be null")
	 private String city;
	 @NotBlank(message = "State name should not be blank")
	 @NotNull(message="State name should not be null")
	 private String State;
	 @NotBlank(message = "Pincode name should not be blank")
	 @NotNull(message="Pincode name should not be null")
	 private String pincode;
	 
	 

}

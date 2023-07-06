package com.HelloSpring.serviceI;

import com.HelloSpring.dto.request.CustomerFnmLnmGenderDTO;
import com.HelloSpring.model.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFnmLnmGenderimpl implements CustomerFnmLnmGenderDTO {
 
	private String firstName;
	private String lastName;
	private Gender gender;
	
	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public Gender getGender() {
		return gender;
	}

}

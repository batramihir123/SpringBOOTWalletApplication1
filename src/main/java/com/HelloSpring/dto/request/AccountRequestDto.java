package com.HelloSpring.dto.request;

import java.sql.Date;

import com.HelloSpring.model.AccountType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDto {
	
	  
	   
	    private int customerid;	
	
		private AccountType accountType;
		
		private double openingBalance;
		
		private Date   openingDate;
		
		private String description;
		
	

}

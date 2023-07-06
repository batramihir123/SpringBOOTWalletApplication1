package com.HelloSpring.dto.request;

import java.time.LocalDate;

import com.HelloSpring.model.Account;
import com.HelloSpring.model.*;
import com.HelloSpring.model.TransactionType;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO 
{
	private int fromAccount;
	
	private int toAccount;
	
	private LocalDate date;
	
	private String description;
	
	private TransactionType transactiontype;
	
	private long amount;

}

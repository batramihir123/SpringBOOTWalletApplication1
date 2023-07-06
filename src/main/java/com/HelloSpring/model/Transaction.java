package com.HelloSpring.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SequenceGenerator(initialValue = 100,name = "accSeq",sequenceName = "accSeq")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "transactionId")
public class Transaction 
{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "accSeq")
	private int TransactionId;
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity = Account.class)
	private Account fromAccount;
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity = Account.class)
	private Account toAccount;
	
	private long amount;
	
	private LocalDate date;
	
	private String description;
	
	@Enumerated(EnumType.STRING)
	private TransactionType transactiontype;
	
	
}

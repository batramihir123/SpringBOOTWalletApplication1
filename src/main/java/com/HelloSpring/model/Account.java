package com.HelloSpring.model;

import java.sql.Date;

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

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(initialValue = 100,name = "accSeq",sequenceName = "accSeq")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "accountNumber")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "accSeq")
	private int accountNumber;
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity = Customer.class)
	@JoinColumn(name="customerFk")
	@JsonIgnoreProperties("accoutns")
	private Customer customer;
	
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	private double openingBalance;
	private Date   openingDate;
	private String description;
	
	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", accountType=" + accountType + ", openingBalance="
				+ openingBalance + ", openingDate=" + openingDate + ", description=" + description + "]";
	}

	 
	
}

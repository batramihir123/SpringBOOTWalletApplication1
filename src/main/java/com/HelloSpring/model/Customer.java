package com.HelloSpring.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "customerId")
public class Customer {
	
	@Id
	@GeneratedValue
	int customerId;
	String firstName;
	String lastName;
	String emailId;
	String contactNo;
	
	@OneToOne
	@JoinColumn(name="addressFk")
	Address address;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@Column(name="pwd")
	String password;
	@Transient
	String confirmPassword;
	private LocalDate registerationDate;
	
	
	@OneToMany(targetEntity=Account.class,mappedBy="customer")
	private List<Account> accoutns=new ArrayList<Account>();

}

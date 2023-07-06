package com.HelloSpring.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Address {
	
	@Id
	@GeneratedValue
	int addressId;
	String addressLine1;
	String addressLine2;
	String city;
	String state;
	String pincode;

}

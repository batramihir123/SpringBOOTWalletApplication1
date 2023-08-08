package com.HelloSpring.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.HelloSpring.security.token.Token;
import com.HelloSpring.security.user.Role;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "customerId")
public class Customer implements UserDetails {
	
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

	@Column(name = "expiryDate")
	private LocalDate expiryDate;
	
	@OneToMany(targetEntity = Token.class,mappedBy = "customer")
	private List<Token> tokens;

	@OneToMany(targetEntity=Account.class,mappedBy="customer")
	private List<Account> accoutns=new ArrayList<Account>();

	private String validationMessage;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Role.USER.getAuthorities();
	}

	@Override
	public String getUsername() {
		return emailId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

package com.HelloSpring.service;




import com.HelloSpring.dto.request.CustomerFnmLnmGenderDTO;
import com.HelloSpring.dto.request.CustomerLoginDTO;
import com.HelloSpring.dto.request.CustomerRequestDto;

import com.HelloSpring.model.Customer;
import java.util.*;

import jakarta.validation.Valid;




public interface CustomerService {

	
	public Integer createCustomer(@Valid CustomerRequestDto customerRequest);

	public Customer getCustomerByCustId(int customerid);
	public Customer getCustomerByCustId1(String CustEmailID);

	public boolean isValidCustByEmailidAndPwd(@Valid CustomerLoginDTO customerLogin);

	public Customer saveCustomer(Customer cust);
	public List<Customer> findByFirstNameLike(String fn);
	public List<Customer> findByFirstNameContaining(String fnm);
	public List<Customer> findByfirstNameContains(String fnm);
	public List<Customer> findByfirstNameIsContaining(String fnm);
	public List<Customer> findByFirstNameIgnoreCase(String fn);
	public List<CustomerFnmLnmGenderDTO> findByLastName(String lnm);
	Boolean existsByEmailIdAndPassword(String email, String password);
}
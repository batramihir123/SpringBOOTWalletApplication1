package com.HelloSpring.service;

import java.util.List;

import com.HelloSpring.model.Address;

public interface AddressService {
	
	
	public Address getAddressByAddressId(int addressId);
	public  List<Address> findByAddressLine2IsNull();
	 public  List<Address> findByAddressLine2IsNotNull();
}

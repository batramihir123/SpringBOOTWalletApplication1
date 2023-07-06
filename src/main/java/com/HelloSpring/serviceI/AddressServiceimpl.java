package com.HelloSpring.serviceI;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HelloSpring.model.Address;
import com.HelloSpring.repo.AddressRepo;
import com.HelloSpring.service.AddressService;
import com.HelloSpring.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AddressServiceimpl  implements AddressService{
	

	@Autowired
	CustomerService cserv;
	@Autowired(required = false)
	AddressRepo ar;


	@Override	
	public Address getAddressByAddressId(int addressId) {
		return ar.findById(addressId).get();
	}

	@Override
	public List<Address> findByAddressLine2IsNull() 
	{
		return ar.findByAddressLine2IsNull();
		
	}

	@Override
	public List<Address> findByAddressLine2IsNotNull() {
		return ar.findByAddressLine2IsNotNull();
	}

}

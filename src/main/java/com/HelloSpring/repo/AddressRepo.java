package com.HelloSpring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HelloSpring.model.Address;



public interface AddressRepo  extends JpaRepository<Address, Integer> {

	public Address getAddressByAddressId(int addressId);
	public  List<Address> findByAddressLine2IsNull();
	public   List<Address> findByAddressLine2IsNotNull();
}

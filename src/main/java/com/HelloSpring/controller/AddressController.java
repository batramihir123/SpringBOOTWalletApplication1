package com.HelloSpring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.HelloSpring.apiresponse.ApiResponse;
import com.HelloSpring.model.Account;
import com.HelloSpring.model.AccountType;
import com.HelloSpring.model.Address;
import com.HelloSpring.repo.AddressRepo;
import com.HelloSpring.service.AddressService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AddressController 
{
	@Autowired
	AddressService as;
	@Autowired 
	AddressRepo ar;
	
	 @GetMapping(value = "/getAddressByAddressId/{addressId}")
	 public ResponseEntity<ApiResponse> getAddressByAddressId( @PathVariable  int addressId)
	    {
	   	 log.info("Finding the Account Order By");
	     Address am = as.getAddressByAddressId(addressId);
	   	 ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"Address  is there",am);
	   	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	 
	    }
	
	 

	 @GetMapping(value = "/findByAddressLine2IsNotNull")
	 public ResponseEntity<ApiResponse> findByAddressLine2IsNotNull( )
	    {
	   	 log.info("Finding the Account Order By");
	     List<Address> am = as.findByAddressLine2IsNotNull();
	   	 ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"Address  is there",am);
	   	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	 
	    }
	
	 
	 
	

}

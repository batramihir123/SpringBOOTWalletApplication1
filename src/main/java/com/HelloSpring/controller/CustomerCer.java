package com.HelloSpring.controller;

import java.util.List;
import com.HelloSpring.dto.request.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.HelloSpring.apiresponse.ApiResponse;
import com.HelloSpring.apiresponse.ApiResponse1;
import com.HelloSpring.dto.request.CustomerLoginDTO;
import com.HelloSpring.dto.request.CustomerRequestDto;
import com.HelloSpring.model.Customer;
import com.HelloSpring.service.AddressService;
import com.HelloSpring.service.CustomerService;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/customer")
@Slf4j
public class CustomerCer {
	@Autowired(required = false)
	CustomerService cs;
	@Autowired(required = false)
	AddressService add;
	
	 @PostMapping(value = "/create")
	 public ResponseEntity<ApiResponse> createCustomer(@RequestBody @Valid CustomerRequestDto customerRequest) 
	 {
	  log.info("inside create method of cusgtomer controller");
		  Integer generatedCustomerId=cs.createCustomer(customerRequest);
		  System.out.println(generatedCustomerId);
	   ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(), "Customer Created Successfully",  generatedCustomerId);
	  return new ResponseEntity<ApiResponse> (apiResponse,HttpStatus.OK);
	 }
	 
	 
	 @PostMapping(value="/isValidUser")
		public ResponseEntity<ApiResponse> isValidUser(@RequestBody  @Valid CustomerLoginDTO customerLogin)
		{
			log.info("authenticating user - valid or not ");
			//customerLogin.
			System.out.println(customerLogin);
			boolean res=cs.isValidCustByEmailidAndPwd(customerLogin);
			ApiResponse apiresponse=new ApiResponse(HttpStatus.OK.value(), "user is validated", res);
			
			return new ResponseEntity<ApiResponse>	(apiresponse,HttpStatus.OK);
		}
	 
	
    @GetMapping(value = "/findByFirstNameLike/{FirstName}")
     public ResponseEntity<ApiResponse> findByFirstNameLike(@PathVariable String FirstName) 
     {
    	 log.info("Finding the Customer By its FirstName");
    	 List<Customer> custom = cs.findByFirstNameLike(FirstName);
    	 System.out.println(FirstName+"..........."+custom);
    	 ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"FirstName user is there",custom);
    	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	 
     }
    
    @GetMapping(value = "findByLastName/{LastName}")
    public ResponseEntity<ApiResponse> findByLastName(@PathVariable String LastName) 
    {
   	 log.info("Finding the Customer By LastName");
   	 List<CustomerFnmLnmGenderDTO > custom = cs.findByLastName(LastName);
   	 
   	 ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"LastName user is there",custom);
   	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	 
    }
    
    @GetMapping(value = "/display")
    public ResponseEntity<ApiResponse> display(
    		@RequestParam (value="PageNo",defaultValue = "0") Integer PageNo,
    		@RequestParam (value="PageSize",defaultValue = "5") Integer PageSize
    		) 
    {
		ApiResponse1 custom = cs.display(PageNo,PageSize);
		 ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"LastName user is there",custom);
	   	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);
    }
     
}



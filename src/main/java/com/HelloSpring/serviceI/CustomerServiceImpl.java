package com.HelloSpring.serviceI;

import java.time.LocalDate;
import com.HelloSpring.GlobalException.*;
import com.HelloSpring.GlobalException.ResourceNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HelloSpring.dto.request.CustomerFnmLnmGenderDTO;
import com.HelloSpring.dto.request.CustomerLoginDTO;
import com.HelloSpring.dto.request.CustomerRequestDto;
import com.HelloSpring.model.Address;
import com.HelloSpring.model.Customer;
import com.HelloSpring.model.Gender;
import com.HelloSpring.repo.AddressRepo;
import com.HelloSpring.repo.CustomerRepo;
import com.HelloSpring.service.CustomerService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired(required = false)
	CustomerRepo cr;
	@Autowired(required = false)
	AddressRepo ar;

	@Override
	public Integer createCustomer(@Valid CustomerRequestDto crDto) {
		log.info("Inside save customer of service with given request"+crDto);
			System.out.println("inside save customer of service with given request "+ crDto);
			 Optional<Customer> optCust=cr.findByEmailIdAndPassword(crDto.getEmailId(),crDto.getPassword());
		  if(optCust.isPresent())
		  {
			  throw new EntityAlreadyExistException("Customer email id is already existing ");
		  }
		  Address add=Address.builder().addressLine1(crDto.getAddressLine1())
		    .addressLine2(crDto.getAddressLine2())
		    .city(crDto.getCity())
		    .state(crDto.getState())
		    .pincode(crDto.getPincode())
		    .build();
		 
		  Address addCreated=ar.save(add);
		  
		  
		  Customer cust=Customer.builder().firstName(crDto.getFirstName()).lastName(crDto.getLastName()).emailId(crDto.getEmailId())
		    .contactNo(crDto.getContactNo()).address(addCreated).gender(crDto.getGender()).password(crDto.getPassword()).registerationDate(LocalDate.now())
		    
		    .build();
		  System.out.println("cust entity is now from builder : "+ cust);
		  log.info("Cust enttity saved",cust);;
		  Customer custCreated=cr.save(cust);
		  
		  
		  
		  return custCreated.getCustomerId();
	}

	@Override
	public Customer getCustomerByCustId(int customerid) {
		return cr.findById(customerid).get();
	}

	@Override
	public boolean isValidCustByEmailidAndPwd(@Valid CustomerLoginDTO customerLogin) {
		Optional<Customer> opt=cr.findByEmailIdAndPassword(customerLogin.getEmailId(), customerLogin.getPassword());
		if(opt.isEmpty())
		  {
			  throw new  ResourceNotFoundException("Invalid Userr");
		  }
		
		return true;
	}

	@Override
	public Customer getCustomerByCustId1(String CustEmailID) {
		System.out.println("............."+CustEmailID);
		return cr.findByEmailId(CustEmailID).get();
	}

	@Override
	public Customer saveCustomer(Customer cust) {
		return cr.save(cust);
	}

	@Override
	public List<Customer> findByFirstNameLike(String fn) {
		List<Customer> cs = cr.findAll();
		if(cs.isEmpty())
		{
			throw new  ResourceNotFoundException("Invalid Userr");
			
		}
		List<Customer> fs = new ArrayList<>();
		System.out.println(".................................................."+cs);
		for(Customer ls:cs)
		{
			if(ls.getFirstName().contains(fn))
			{
				fs.add(ls);
			}
		}
		System.out.println("............................"+fs);
		return fs;
		
	}
	@Override
	public List<Customer> findByFirstNameContaining(String fnm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> findByfirstNameContains(String fnm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> findByfirstNameIsContaining(String fnm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> findByFirstNameIgnoreCase(String fn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean existsByEmailIdAndPassword(String email, String password) {
		Optional<Customer> opt=cr.findByEmailIdAndPassword(email, password);
		if(opt.isEmpty())
		  {
			  throw new  ResourceNotFoundException("Invalid Userr");
		  }
		
		return true;
	}

	@Override
	public List<CustomerFnmLnmGenderDTO> findByLastName(String lnm) {
		List<Customer> cs = cr.findAll();
		List<CustomerFnmLnmGenderDTO> f = new ArrayList<>();
		for(Customer c : cs)
		{
			String firstName = c.getFirstName();
			String lastName = c.getLastName();
			Gender gender = c.getGender();
			
			CustomerFnmLnmGenderimpl d = new CustomerFnmLnmGenderimpl();
			d.setFirstName(firstName);
			d.setLastName(lastName);
			d.setGender(gender);
			
			f.add(d);
		}
		
		return f;
	}


}

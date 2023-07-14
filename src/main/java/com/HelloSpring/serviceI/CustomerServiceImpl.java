package com.HelloSpring.serviceI;

import java.time.LocalDate;
import com.HelloSpring.GlobalException.*;
import com.HelloSpring.GlobalException.ResourceNotFoundException;
import com.HelloSpring.apiresponse.ApiResponse1;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
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

	@Autowired
	private JavaMailSender message;

	@Value("${ExpiryDays}")
    Long days;


	/*
	Here the customer is creating using the builder function
	 */
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
		    .contactNo(crDto.getContactNo()).address(addCreated).gender(crDto.getGender()).password(crDto.getPassword())
				  .registerationDate(LocalDate.now()).expiryDate(LocalDate.now().plusDays(days))
		    
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


	/*
	Function for finding out the valid user with his mail id and password
	 */
	@Override
	public boolean isValidCustByEmailidAndPwd(@Valid CustomerLoginDTO customerLogin) {
		Optional<Customer> opt=cr.findByEmailIdAndPassword(customerLogin.getEmailId(), customerLogin.getPassword());
		if(opt.isEmpty())
		  {
			  throw new  ResourceNotFoundException("Invalid Userr");
		  }
		
		return true;
	}
	/*
	Function to find the customer by his mail id
	 */

	@Override
	public Customer getCustomerByCustId1(String CustEmailID) {
		System.out.println("............."+CustEmailID);
		return cr.findByEmailId(CustEmailID).get();
	}

	@Override
	public Customer saveCustomer(Customer cust) {
		return cr.save(cust);
	}


	/*
	Here the customer is getting finded by his firstname
	 */
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

	/*
	Here the pagination is used to get the no of the pages along with the no of rows you want to
	and here I have creATED the seprate response to showup the extra details totlelement and all
	 */
	
	public ApiResponse1 display(Integer PageNo , Integer PageSize) {

		Pageable p = PageRequest.of(PageNo,PageSize);
		Page<Customer> pagepost = cr.findAll(p);
		List<Customer> all = pagepost.getContent();
	
		ApiResponse1 apiresp = new ApiResponse1();
		apiresp.setContent(all);
		apiresp.setPageNumber(pagepost.getNumber());
		apiresp.setPageSize(pagepost.getSize());
		apiresp.setLastPage(pagepost.isLast());
		apiresp.setTotalElement(pagepost.getNumberOfElements());
		apiresp.setTotalPage(pagepost.getTotalPages());
		return apiresp;
	}


	/*
	Here the Scheduler Annotation is applied along with the cron expression to sent the mail
	after Cetrain period of interval
	Cron Expression --> * ->seconds->
	-->*->Minutes->0-59
	--->*->hours->12
	-->*->>Day of the month
	-->*-->Month-->
	---->*-->Day of the week
	-->*->>year
	 */
	@Scheduled(cron = "0 1 * * * *")
	public void SendExpiryDate()
	{
		log.info("Here is the Expiry Mail Scheduler");
		List<Customer> cus = cr.findByExpiryDateOrExpiryDate(LocalDate.now(),LocalDate.now().plusDays(days));
		if(cus.isEmpty())
		{
			throw new  ResourceNotFoundException("Invalid Userr");
		}
		for(Customer C : cus)
		{
			String toEmail = C.getEmailId();
			sendmail(toEmail,"Wallet Account is Expired","Dear Customer" + ""+"Your Account will get Expired on " + C.getExpiryDate()+
					"Please Get Your Premium Recharge" );
		}
	}


	/*
	Here its main java class inbuilt package use to sent the mail along with the functions
	and the object of mailsender is created to send the particular message
	 */
	@Override
	public void sendmail(String toMail, String Subject, String Body) {
		SimpleMailMessage msg= new SimpleMailMessage();

		msg.setFrom("mihirbatra97@gmail.com");
		msg.setTo(toMail);
		msg.setSubject(Subject);
		msg.setText(Body);
		message.send(msg);
		log.info("Mail Sent SuccessFully");
	}

}

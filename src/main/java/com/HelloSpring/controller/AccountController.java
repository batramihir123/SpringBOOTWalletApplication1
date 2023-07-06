package com.HelloSpring.controller;





import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.HelloSpring.apiresponse.ApiResponse;
import com.HelloSpring.dto.request.AccountRequestDto;

import com.HelloSpring.model.Account;
import com.HelloSpring.model.AccountType;
import com.HelloSpring.repo.AccountRepo;
import com.HelloSpring.service.AccountService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class AccountController {

	@Autowired
	AccountService acr;
	@Autowired
	AccountRepo ar;
	@PostMapping(value="/create")
	public ResponseEntity<ApiResponse> createAccount(@RequestBody @Valid AccountRequestDto accountRequest)
	{
		log.info("creating a new Account");
		System.out.println(accountRequest);//AccountRequestDTO(accountNumber=1, customerId=1, accountType=CURRENT, openingBalance=10, openingDate=2022-09-29, description=desc)
		
		Account accountNumber=acr.saveAccount(accountRequest);
		
		ApiResponse apiresponse=new ApiResponse(HttpStatus.OK.value()	, "account created successfully", accountNumber);
		return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.OK);
	}
	
	 @GetMapping(value = "/AccountsByCustEmailId/{emailid}")
	 public ResponseEntity<ApiResponse> AccountsByCustEmailId(@PathVariable String emailid) 
	    {
	   	 log.info("Finding the Account");
	   	 List<Account > ac= acr.getAccountsByCustEmailId(emailid);
	   	 System.out.println(emailid+"..........."+ac);
	   	 ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"Account user is there",ac);
	   	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	 
	    }
	 
	 @GetMapping(value = "/OrderByOpeningBalanceAsc")
	 public ResponseEntity<ApiResponse> OrderByOpeningBalanceAsc() 
	    {
	   	 log.info("Finding the Account Order By");
	   	 List<Account > ac= acr.findByOrderByOpeningBalanceAsc();
	   	 
	   	 ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"Account  is there",ac);
	   	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	 
	    }

	 @GetMapping(value = "/AccountsLessThanOpBal/{openingBalance}")
	 public ResponseEntity<ApiResponse> getAccountsLessThanOpBal(@PathVariable double openingBalance) 
	    {
	   	 log.info("Finding the Account Order By");
	   	 List<Account > ac= acr.getAccountsLessThanOpBal(openingBalance);
	   	 
	   	 ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"Account  is there",ac);
	   	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	 
	    }

	 @GetMapping(value = "/getAccountByAccNumber/{accountNumber}")
	 public ResponseEntity<ApiResponse> getAccountByAccNumber(@PathVariable int accountNumber) 
	    {
	   	 log.info("Finding the Account Order By");
	   	 Account ac= acr.getAccountByAccNumber(accountNumber);
	   	 
	   	 ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"Account  is there",ac);
	   	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	 
	    }
	 
	 

	 @GetMapping(value = "/findDistinctByAccountTypeAndOpeningBalance/{accType}/{openingBalance}")
	 public ResponseEntity<ApiResponse> getAccountByAccNumber(@PathVariable AccountType accType, @PathVariable double openingBalance) 
	    {
	   	 log.info("Finding the Account Order By");
	   	List<Account> acs = acr.findDistinctByAccountTypeAndOpeningBalance(accType, openingBalance);
	   	 
	   	 ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"Account  is there",acs);
	   	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	 
	    }
	 
	 
	 @GetMapping(value = "/findByOpeningDateBetween/{startDate}/{endDate}")
	 public ResponseEntity<ApiResponse> findByOpeningDateBetween(@PathVariable LocalDate startDate,@PathVariable LocalDate endDate) 
	    {
	   	 log.info("Finding the Account Order By");
	   	List<Account> acs = acr.findByOpeningDateBetween(startDate, endDate);
	   	 
	   	 ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"Account  is there",acs);
	   	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	 
	    }
	 
	 
	 @GetMapping(value = "/findByAccountTypeIn/{accTypes}")
	 public ResponseEntity<ApiResponse> findByAccountTypeIn( @PathVariable List<AccountType> accTypes)
	    {
	   	 log.info("Finding the Account Order By");
	   	List<Account> acs = acr.findByAccountTypeIn(accTypes);
	   	 
	   	 ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"Account  is there",acs);
	   	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	 
	    }
	 
	 
	 
}

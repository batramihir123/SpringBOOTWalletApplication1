package com.HelloSpring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.HelloSpring.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.HelloSpring.apiresponse.ApiResponse;
import com.HelloSpring.dto.request.TransactionDTO;
import com.HelloSpring.model.Transaction;
import com.HelloSpring.repo.TransactionRepo;
import com.HelloSpring.service.AccountService;
import com.HelloSpring.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TransactionController 
{
	@Autowired
	TransactionService tk;
	@Autowired
	TransactionRepo tm;
	@Autowired
	AccountService as;
	
	@PostMapping(value = "/deposit")
	public ResponseEntity<ApiResponse> depositAmountInAccount(@RequestBody TransactionDTO tdd)
	{
		System.out.println(tdd.getToAccount());	
		Transaction trans = tk.depositAmountInAccount(tdd);
		ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"Transaction Depsoited",trans);
	   	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);
	}
	
	@PostMapping(value = "/withdraw")
	public ResponseEntity<ApiResponse> WithDrawAmountfromAccount(@RequestBody TransactionDTO tdd)
	{
		Transaction trans = tk.WithDrawAmountfromAccount(tdd);
		ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"Transaction WithdrawDone",trans);
	   	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);
	}
	
	@PostMapping(value = "/fundtransfer")
	public ResponseEntity<ApiResponse>  fundTransferFunction(@RequestBody TransactionDTO tdd) 
	{
		Transaction trans = tk.fundTransferFunction(tdd);
		ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"Fund is transfered",trans);
	   	return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);
	}
	
	@GetMapping(value = "/SummaryTransaction/{accountNumber}")
	public ResponseEntity<ApiResponse>  getTransactionsByAccountNumber(@PathVariable int accountNumber) 
	{
		System.out.println(accountNumber);
		List<Transaction> trans = tk.getTransactionsByAccountNumber(accountNumber);
		ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"Here is the Summary",trans);
	   	return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);
	}
	
}

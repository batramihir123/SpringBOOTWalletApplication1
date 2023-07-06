package com.HelloSpring.serviceI;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HelloSpring.GlobalException.ResourceNotFoundException;
import com.HelloSpring.dto.request.TransactionDTO;
import com.HelloSpring.model.Account;
import com.HelloSpring.model.Transaction;
import com.HelloSpring.repo.AccountRepo;
import com.HelloSpring.repo.TransactionRepo;
import com.HelloSpring.service.AccountService;
import com.HelloSpring.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionServiceImpl  implements TransactionService
{
	@Autowired
	TransactionService ts;
	@Autowired
	TransactionRepo tp;
	@Autowired
	AccountService ac;
	@Autowired
	AccountRepo accm;

	@Override
	public Transaction saveTransaction(Transaction trans) {
				
		return null;
	}

	@Override
	public Transaction WithDrawAmountfromAccount(TransactionDTO tdd) 
	{
		Account as = ac.getAccountByAccNumber(tdd.getFromAccount());
		int opbal = (int) as.getOpeningBalance();
		if(opbal<tdd.getAmount())
		{
			  throw new  ResourceNotFoundException("Your account Balance is very less");
		}
		as.setOpeningBalance(opbal-tdd.getAmount());
		
		
		Transaction tc= Transaction.builder().amount(tdd.getAmount())
				.date(tdd.getDate()).description(tdd.getDescription()).fromAccount(as).transactiontype(tdd.getTransactiontype()).build();
		
		Transaction t = tp.save(tc);
		return t;
	}

	@Override
	public Transaction fundTransferFunction(TransactionDTO tdd) 
	{
		
		Account a1 = ac.getAccountByAccNumber(tdd.getToAccount());
		Account a2 = ac.getAccountByAccNumber(tdd.getFromAccount());
		int opbal = (int) a2.getOpeningBalance();
		if(opbal<tdd.getAmount())
		{
			  throw new  ResourceNotFoundException("Your account Balance is very less");
		}
		a2.setOpeningBalance(opbal-tdd.getAmount());
		a1.setOpeningBalance(opbal + tdd.getAmount());
		
		Transaction tc= Transaction.builder().amount(tdd.getAmount())
				.date(tdd.getDate()).description(tdd.getDescription()).toAccount(a1).fromAccount(a2).transactiontype(tdd.getTransactiontype()).build();
		
		Transaction t = tp.save(tc);
		return t;
	}

	@Override
	public Transaction depositAmountInAccount(TransactionDTO tdd) {
	
		System.out.println(tdd.getToAccount());
		
		Account as = ac.getAccountByAccNumber(tdd.getToAccount());
		int opbal = (int) as.getOpeningBalance();
		System.out.println(opbal+"...............................");
		int updatedBal = (int) (opbal + tdd.getAmount());
		updateOpeningBalanceByAccountNumber(as,updatedBal);
		System.out.println("....................................");
		
		Transaction tc= Transaction.builder().amount(tdd.getAmount())
				.date(tdd.getDate()).description(tdd.getDescription()).toAccount(as).transactiontype(tdd.getTransactiontype()).build();
		
		Transaction t = tp.save(tc);
		return t;
		
	}

	@Override
	public void updateOpeningBalanceByAccountNumber(Account acc, double newOpeningBalance) 
	{

		acc.setOpeningBalance(newOpeningBalance);  
	} 

	@Override
	public List<Transaction> getTransactionsByAccountNumber(int accountNumber) 
	{
		System.out.println(accountNumber+">>>>>");
		
		List<Transaction> trans = tp.findByFromAccountAccountNumberOrToAccountAccountNumber(accountNumber,accountNumber);
		System.out.println(">>>>.......................>>"+trans);
		return trans;
	}
	

}

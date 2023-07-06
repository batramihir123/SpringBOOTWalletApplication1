package com.HelloSpring.serviceI;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.HelloSpring.GlobalException.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HelloSpring.GlobalException.EntityAlreadyExistException;
import com.HelloSpring.dto.request.AccountRequestDto;
import com.HelloSpring.model.Account;
import com.HelloSpring.model.AccountType;
import com.HelloSpring.model.Customer;
import com.HelloSpring.repo.AccountRepo;
import com.HelloSpring.service.AccountService;
import com.HelloSpring.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	AccountRepo accr;
	@Autowired
	CustomerService cserv;
	
	@Override
	public Account saveAccount(AccountRequestDto account) {

		Optional<Account> ac=accr.findByCustomerCustomerIdAndAccountType(account.getCustomerid(), account.getAccountType());
		if(ac.isPresent())
		{
			 throw new EntityAlreadyExistException("Account  is already existing ");
	    }
		
			Customer cust=cserv.getCustomerByCustId(account.getCustomerid());
			
			
		 
			Account acc=Account.builder().
					accountType(account.getAccountType())
					.customer(cust)
					.description(account.getDescription())
					.openingBalance(account.getOpeningBalance())
					.openingDate(account.getOpeningDate()).build();
		
			return accr.save(acc);
	}

	@Override
	public List<Account> getAccountsByCustId(int intCustId) {
		log.info("getting accounts by custid for customer id "+ intCustId);
		
		
		List<Account> lstAccounts = accr.findByCustomerCustomerId(intCustId);
	
		if(lstAccounts.isEmpty())
		{
			throw new ResourceNotFoundException("No Account found for customer id "+ intCustId);
		}
		return lstAccounts;
	}

	@Override
	public List<Account> getAccountsByCustEmailId(String custEmailId) {
		System.out.println(custEmailId);
		Customer cust= cserv.getCustomerByCustId1(custEmailId);
		System.out.println(cust);
		List<Account> lst=getAccountsByCustId(cust.getCustomerId());
		return lst;
	}

	@Override
	public List<Account> getAccountsLessThanOpBal(double openingBalance) {
		log.info("inside  getAccountsLessThanOpBal");
		List<Account> lst=accr.findByOpeningBalanceLessThan(openingBalance);
		return lst;
	}

	@Override
	public List<Account> getAccountsLessThanEqualOpBal(double openingBalance) {
		log.info("inside  getAccountsLessThanOpBalEqual");
		List<Account> lst=accr.findByOpeningBalanceLessThan(openingBalance);
		return lst;
	}

	@Override
	public Account getAccountByAccNumber(int accountNumber) {
		Optional<Account> optAcc = java.util.Optional.empty();
		try {
			optAcc = accr.findById(accountNumber);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		if(optAcc.isEmpty())
		{
			throw new ResourceNotFoundException("Account is not existing for account number "+ accountNumber);
		}
		return optAcc.get();
	}

	@Override
	public List<Account> findDistinctByAccountTypeAndOpeningBalance(AccountType accType, double openingBalance) {

		log.info("inside find distinct By accctype and op balance - accType "+ accType+" op bal= "+ openingBalance);		
		List<Account> lst=	accr.findDistinctByAccountTypeAndOpeningBalance(accType, openingBalance);
		return lst;
		
	}

	@Override
	public List<Account> findDistinctByOpeningBalance(double openingBalance) {
		log.info("inside find distinct By  op balance - "+ openingBalance);		
		List<Account> lst=	accr.findDistinctByOpeningBalance(openingBalance);
		return lst;
	}

	
	@Override
	public List<Account> findByOpeningBalanceGreaterThan(double openingBalance) {
		log.info("inside findByOpeningBalanceGreaterThan - "+ openingBalance);		
		List<Account> lst=	accr.findByOpeningBalanceGreaterThan(openingBalance);
		return lst;
	}

	@Override
	public List<Account> findByOpeningDateBetween(LocalDate startDate, LocalDate endDate) {
		log.info(startDate.toString());
		log.info(endDate.toString());
		return accr.findByOpeningDateBetween(startDate, endDate);
	}

	@Override
	public List<Account> findByOpeningDateAfter(LocalDate dt) {
		return accr.findByOpeningDateAfter(dt);
	}

	@Override
	public List<Account> findByOrderByOpeningBalanceAsc() {
		return accr.findByOrderByOpeningBalanceDesc();
	}

	@Override
	public List<Account> findByOpeningBalanceNot(double ob) {
		return accr.findByOpeningBalanceNot(ob);
	}

	@Override
	public List<Account> findByAccountTypeIn(List<AccountType> accTypes) {
		return accr.findByAccountTypeNotIn(accTypes);
	}

}

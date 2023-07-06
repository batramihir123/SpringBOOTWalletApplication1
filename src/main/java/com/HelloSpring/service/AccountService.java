package com.HelloSpring.service;




import com.HelloSpring.dto.request.AccountRequestDto;

import com.HelloSpring.model.Account;
import com.HelloSpring.model.AccountType;

import java.time.LocalDate;
import java.util.*;




public interface AccountService {

	public Account saveAccount(AccountRequestDto account);
	public List<Account> getAccountsByCustId(int intCustId);
	public List<Account> getAccountsByCustEmailId(String custEmailId);
	public List<Account> getAccountsLessThanOpBal(double openingBalance);
	public List<Account> getAccountsLessThanEqualOpBal(double openingBalance);
	public Account getAccountByAccNumber(int accountNumber);
	
	public List<Account>  findDistinctByAccountTypeAndOpeningBalance(AccountType accType,double openingBalance);
	public List<Account>  findDistinctByOpeningBalance(double openingBalance);
	//public List<String> getDistinctAccTypes();
	public List<Account> findByOpeningBalanceGreaterThan(double openingBalance);
	public List<Account> findByOpeningDateBetween(LocalDate startDate,LocalDate endDate);
	//public List<String> getDistinctAccTypes();
	public List<Account> findByOpeningDateAfter(LocalDate dt);
	public List<Account> findByOrderByOpeningBalanceAsc();
	public List<Account> findByOpeningBalanceNot(double ob);
	//select * from account where account_type in ('CURRENT','RD','LOAN');
	public List<Account> findByAccountTypeIn(List<AccountType> accTypes);
	

}

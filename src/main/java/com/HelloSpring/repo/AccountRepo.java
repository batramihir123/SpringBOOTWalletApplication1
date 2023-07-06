package com.HelloSpring.repo;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HelloSpring.model.Account;
import com.HelloSpring.model.AccountType;




public interface AccountRepo extends JpaRepository<Account, Integer>{

	Optional<Account> findByCustomerCustomerIdAndAccountType(int customerid, AccountType accountType);

	 List<Account> findByCustomerCustomerId(int customerId);

	List<Account> findByOpeningBalanceLessThan(double openingBalance);

	List<Account> findDistinctByAccountTypeAndOpeningBalance(AccountType accType, double openingBalance);

	List<Account> findDistinctByOpeningBalance(double openingBalance);

	List<Account> findByOpeningDateBetween(LocalDate startDate, LocalDate endDate);

	List<Account> findByOpeningBalanceGreaterThan(double openingBalance);

	List<Account> findByOpeningDateAfter(LocalDate dt);

	List<Account> findByOrderByOpeningBalanceDesc();

	List<Account> findByOpeningBalanceNot(double ob);

	List<Account> findByAccountTypeNotIn(List<AccountType> accTypes);
	 Optional<Account> findByAccountNumber(int accountNumber);
	
	}


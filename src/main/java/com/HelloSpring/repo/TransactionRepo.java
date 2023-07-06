package com.HelloSpring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HelloSpring.model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Integer>{

	List<Transaction> findByFromAccountAccountNumberOrToAccountAccountNumber(int fromAccountNumber, int toAccountNumber);
}

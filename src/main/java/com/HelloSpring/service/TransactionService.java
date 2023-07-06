package com.HelloSpring.service;

import java.util.List;

import com.HelloSpring.dto.request.TransactionDTO;
import com.HelloSpring.model.Account;
import com.HelloSpring.model.Transaction;

public interface TransactionService {
	public Transaction saveTransaction(Transaction trans);
	public Transaction WithDrawAmountfromAccount(TransactionDTO tdd);
	public Transaction fundTransferFunction(TransactionDTO tdd);
	public Transaction depositAmountInAccount(TransactionDTO tdd);
	public void updateOpeningBalanceByAccountNumber(Account acc,double newOpeningBalance);
	public List<Transaction> getTransactionsByAccountNumber(int accountNumber);
}

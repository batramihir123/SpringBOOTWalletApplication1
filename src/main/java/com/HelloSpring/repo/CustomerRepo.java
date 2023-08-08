package com.HelloSpring.repo;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.HelloSpring.model.Customer;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {

	Optional<Customer> findByEmailIdAndPassword(String emailId , String Password);
	Optional<Customer> findByEmailId(String emailId);
	List<Customer> findByExpiryDateOrExpiryDate(LocalDate before , LocalDate after);
	Page<Customer> findByFirstNameIsContainingOrLastNameIsContainingOrEmailIdIsContaining(String firstName, String lastName, String emailId, Pageable pageable);

	boolean existsByCustomerId(int customerId);
}

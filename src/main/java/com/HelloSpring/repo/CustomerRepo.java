package com.HelloSpring.repo;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HelloSpring.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {

	Optional<Customer> findByEmailIdAndPassword(String emailId , String Password);
	Optional<Customer> findByEmailId(String emailId);


}

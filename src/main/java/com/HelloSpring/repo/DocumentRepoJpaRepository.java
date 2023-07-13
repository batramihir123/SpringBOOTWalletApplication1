package com.HelloSpring.repo;

import com.HelloSpring.model.Customer;
import com.HelloSpring.model.CustomerDocuments;
import com.HelloSpring.model.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepoJpaRepository extends JpaRepository<CustomerDocuments, Integer> {

    Optional<CustomerDocuments> findBypath(String path);
    Optional<CustomerDocuments> findByCustomerCustomerIdAndDocumentType(Integer CustomerId,DocumentType documentType);
    Optional<CustomerDocuments> findByCustomer_CustomerId(int customerId);

}


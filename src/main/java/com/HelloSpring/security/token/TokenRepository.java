package com.HelloSpring.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = "select t from Token t inner join Customer c on t.customer.customerId = c.customerId where c.customerId = :id and (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);
}

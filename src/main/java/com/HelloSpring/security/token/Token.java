package com.HelloSpring.security.token;


import com.HelloSpring.model.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token
{
    @Id
    @GeneratedValue
    public  Integer id;

    @Column(unique = true)
    public  String token;

    @Enumerated
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    public Customer customer;
}

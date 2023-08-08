package com.HelloSpring.security.config;

import com.HelloSpring.GlobalException.ResourceNotFoundException;
import com.HelloSpring.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig
{
    private  final CustomerRepo customerRepo;

    @Bean
    public UserDetailsService userDetailsService()
    {
        return username -> customerRepo.findByEmailId(username)
                .orElseThrow(()->new ResourceNotFoundException("user not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider  = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
    {
        return  configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    { return new BCryptPasswordEncoder();}
}

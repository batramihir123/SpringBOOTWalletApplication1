package com.HelloSpring.serviceI;

import com.HelloSpring.apiresponse.AuthenticationResponse;
import com.HelloSpring.dto.request.CustomerLoginDTO;
import com.HelloSpring.model.Customer;
import com.HelloSpring.repo.CustomerRepo;
import com.HelloSpring.security.config.JwtService;
import com.HelloSpring.security.token.Token;
import com.HelloSpring.security.token.TokenRepository;
import com.HelloSpring.security.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Data
@Service
@Slf4j
public class AuthService
{
    private final CustomerRepo repository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse authenticate(CustomerLoginDTO request) {

        log.info("here it is ,,,,,,,,,,,");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmailId(),
                        request.getPassword()
                )
        );
        log.info("After one");
        Customer user = repository.findByEmailId(request.getEmailId())
                .orElseThrow();
        log.info("After 2");

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.genreateRefreshToken(user);
        log.info("After this");

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(Customer user, String jwtToken) {
        Token token = Token.builder()
                .customer(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Customer user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getCustomerId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            Customer user = repository.findByEmailId(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}

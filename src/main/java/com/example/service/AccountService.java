package com.example.service;
import com.example.entity.*;
import com.example.exception.*;

import javax.naming.AuthenticationException;
import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import java.util.Optional;

@Service
@Transactional
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) throws RuntimeException{
        if(account.getUsername().isBlank() || account.getPassword().length() < 4 ){
            throw new IllegalArgumentException("Username Cannot be empty and password must be 4 characters or more");
        }
        if(accountRepository.existsByUsername(account.getUsername())){
            throw new ResourceConflictException("Username is already registered");
        }
        return accountRepository.save(account);
    }

    public Account login(Account account) throws RuntimeException, AuthenticationException{
        Optional<Account> optional = accountRepository.findByUsername(account.getUsername());
        if(!optional.isPresent()){
            throw new AccountNotFoundException("Account withe Username "+ account.getUsername() + " Not Found");
        } 
        Account ac = optional.get();
        
        if(!account.getPassword().equals(ac.getPassword())){
            throw new AuthenticationException("Username and Password Do not match");
        }

        return ac;
    } 
}

/**
 * Code Written by: Emanuel Ruiz
 */
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

    /**
     * Constructor with Dependency injection of a repository 
     * @param accountRepository
     */
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /**
     * Registers new account 
     * @param account
     * @return
     * @throws RuntimeException if properties are blank or fail minimum requirement, further throws exception if Resource 
     *                          already exists in the database
     */
    public Account registerAccount(Account account) throws RuntimeException{
        if(account.getUsername().isBlank() || account.getPassword().length() < 4 ){
            throw new IllegalArgumentException("Username Cannot be empty and password must be 4 characters or more");
        }
        if(accountRepository.existsByUsername(account.getUsername())){
            throw new ResourceConflictException("Username is already registered");
        }
        return accountRepository.save(account);
    }

    /**
     * Authenticates user by comparing account values
     * @param account
     * @return
     * @throws RuntimeException if account is not present in the database
     * @throws AuthenticationException if password and username combination do not match
     */
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

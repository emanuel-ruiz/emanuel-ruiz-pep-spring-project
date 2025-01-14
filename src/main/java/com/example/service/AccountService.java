package com.example.service;
import com.example.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;

import javassist.NotFoundException;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) throws RuntimeException{
        if(accountRepository.existsByUsername(account.getUsername())){
            throw new RuntimeException();
        }
        return accountRepository.save(account);
    }
}

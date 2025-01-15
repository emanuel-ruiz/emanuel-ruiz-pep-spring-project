package com.example.controller;
import com.example.entity.*;
import com.example.exception.ResourceConflictException;
import com.example.exception.ResourceNotFoundException;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@Controller
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService aService, MessageService mService){
        this.accountService = aService;
        this.messageService = mService;
    }
    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> register(@RequestBody Account newAccount){
        return ResponseEntity.status(200).body(accountService.registerAccount(newAccount));
    }

    /*TODO Change the Response Entity */
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<?> login(@RequestBody Account newAccount){

        try {
            return ResponseEntity.status(200).body(accountService.login(newAccount));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } 
        
    }

    /*TODO temp message */
    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message newMessage){
        return ResponseEntity.status(200).body(newMessage);
    }

    /*TODO temp messages */
    @GetMapping("/messages")
    public @ResponseBody List<Message> getMessages(){
        List<Message> messages = new ArrayList<>();
        messages = messageService.getAllMessages();
        return messages;
    }

    @GetMapping("/messages/{messageID}")
    public @ResponseBody ResponseEntity<Message> getMessageByID(@PathVariable int messageID){
        return ResponseEntity.status(200).body(messageService.getMessageById(messageID));
    }

    @DeleteMapping("messages/{messageID}")
    public @ResponseBody ResponseEntity<Integer> deleteMessage(@PathVariable int messageID){
        return ResponseEntity.status(200).body(messageService.deleteMessageById(messageID));
    }

    @PatchMapping("messages/{messageID}")
    public @ResponseBody ResponseEntity<Integer> updatMessage(@PathVariable int messageID,@RequestBody String message){
        return ResponseEntity.status(200).body(messageService.updateMessageById(messageID, message));
    }

    @GetMapping("/accounts/{accountId}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable int accountId)
    {
        return ResponseEntity.status(200).body(messageService.getMessagesByUser(accountId));
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<String> resourceConflictExceptionHandler(ResourceConflictException ex){
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> authenticationExceptionHandler(AuthenticationException ex){
        return ResponseEntity.status(401).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException ex){
        return ResponseEntity.status(400).body(ex.getMessage());
    }

}

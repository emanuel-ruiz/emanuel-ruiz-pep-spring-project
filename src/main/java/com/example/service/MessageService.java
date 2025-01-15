package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.*;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.List;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) throws RuntimeException {
        if(message.getMessageText().isBlank() || message.getMessageText().length() > 255 ){
            throw new IllegalArgumentException("Message cannot be blank or be larger than 255 characters");
        }
        if(!accountRepository.existsById(message.getPostedBy())){
            throw new IllegalArgumentException("Posted by user is not registered");
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        List<Message> messages = (List) messageRepository.findAll();
        return messages;
    }
}

/**
 * Code Written by: Emanuel Ruiz
 * 
 */
package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.*;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class that implements crud operations using the message and account repository
 */
@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    /**
     * Constructor with Depedency Injection or repositories
     * @param messageRepository
     * @param accountRepository
     */
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Calls the messageRepository to create new messages
     * 
     * @param message
     * @return
     * @throws RuntimeException Throws exception for empty messageText or messages to large
     */
    public Message createMessage(Message message) throws RuntimeException {
        if(message.getMessageText().isBlank() || message.getMessageText().length() > 255 ){
            throw new IllegalArgumentException("Message cannot be blank or be larger than 255 characters");
        }
        if(!accountRepository.existsById(message.getPostedBy())){
            throw new IllegalArgumentException("Posted by user is not registered");
        }

        return messageRepository.save(message);
    }

    /**
     * Uses the messageRepository to retrieve all messages
     * @return
     */
    public List<Message> getAllMessages(){
        List<Message> messages = (List) messageRepository.findAll();
        return messages;
    }

    /**
     * Uses messageRepository to get a message according to messageId value
     * @param messageId
     * @return
     */
    public Message getMessageById(int messageId){
        Optional<Message> message = messageRepository.findById(messageId);
        if(!message.isPresent()){
            return null;
        }
        return message.get();
    }

    /**
     * Deletes message from database
     * @param messageId
     * @return number of rows affected
     */
    public Integer deleteMessageById(int messageId){
        if(messageRepository.existsById(messageId)){
            messageRepository.deleteById(messageId);
            return 1;
        }
        return null;
    }

    /**
     * Updates message in the database
     * @param messageId
     * @param messageText
     * @return
     * @throws RuntimeException for empty messageText and messageText to large or message not found
     */
    public Integer updateMessageById(int messageId, String messageText) throws RuntimeException{
        if(messageText.isBlank() || messageText.length() > 255){
            throw new IllegalArgumentException("Message Text cannot be blank or longer than 255 characters");
        }
        Optional<Message> message = messageRepository.findById(messageId);
        if(!message.isPresent()){
            throw new ResourceNotFoundException("Message with message_id " + messageId + " Not Found");
        }
        Message newMessage = message.get();
        newMessage.setMessageText(messageText);
        messageRepository.save(newMessage);
        return 1;
    }

    /**
     * Retrives all message made by specified account
     * @param user_id
     * @return
     */
    public List<Message> getMessagesByUser(int user_id){
        List<Message> user_messages = new ArrayList<>();
        user_messages = messageRepository.findByPostedBy(user_id);
        return user_messages;
    }
}

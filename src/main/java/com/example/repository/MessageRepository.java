/**
 * Code Written by: Emanuel Ruiz
 */
package com.example.repository;

import java.util.List;

import com.example.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    
    @Query("SELECT m FROM Message m WHERE m.postedBy = :postedBy")
    List<Message> findByPostedBy(@Param("postedBy") Integer postedBy);
}

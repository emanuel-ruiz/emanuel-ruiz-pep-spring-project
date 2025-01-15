/**
 * Code Written by: Emanuel Ruiz
 */
package com.example.repository;
import com.example.entity.*;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    boolean existsByUsername(String username);

    Optional<Account> findByUsername(String username);
}

package com.example.repository;

import com.example.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface  AccountRepo extends JpaRepository<Account, Long> {


}

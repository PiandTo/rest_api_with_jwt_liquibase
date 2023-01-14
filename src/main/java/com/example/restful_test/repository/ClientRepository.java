package com.example.restful_test.repository;

import com.example.restful_test.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findClientByLogin(String name);
}

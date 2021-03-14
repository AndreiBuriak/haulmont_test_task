package com.example.demo.repo;

import com.example.demo.backend.Client;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ClientRepo extends JpaRepository <Client, Integer> {
    @Query ("from Client c where c.name like concat('%', :name, '%')")
    List<Client> findByName (@Param("name") String name);
}

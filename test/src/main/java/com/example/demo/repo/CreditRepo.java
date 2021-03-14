package com.example.demo.repo;


import com.example.demo.backend.Client;
import com.example.demo.backend.Credit;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditRepo extends JpaRepository<Credit, Integer> {
    @Query("from Credit c where c.name like concat('%', :name, '%')")
    List<Credit> findByName (@Param("name") String name);
}

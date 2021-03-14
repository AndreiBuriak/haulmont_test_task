package com.example.demo.repo;

import com.example.demo.backend.KeyForOffer;
import com.example.demo.backend.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferRepo extends JpaRepository<Offer, KeyForOffer> {
    @Query ("from Offer o where o.client.name like concat('%', :name, '%')")
    List<Offer> findByClientName (@Param("name") String name);

    @Query ("from Offer o where o.credit.name like concat('%', :name, '%')")
    List<Offer> findByCreditName (@Param("name") String name);
}

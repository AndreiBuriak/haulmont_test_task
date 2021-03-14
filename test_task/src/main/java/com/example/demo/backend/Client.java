package com.example.demo.backend;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;
    String telephone;
    String email;
    String document;
    @OneToMany(mappedBy = "client", targetEntity = Offer.class, fetch = FetchType.EAGER)
    List<Offer> offers;
}

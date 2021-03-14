package com.example.demo.backend;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Credit {
    @Id
    @GeneratedValue
    Integer id;
    String name;
    Integer limit;
    Integer percent;
    @OneToMany(mappedBy = "credit", targetEntity = Offer.class, fetch = FetchType.EAGER)
    List<Offer> offers;
}

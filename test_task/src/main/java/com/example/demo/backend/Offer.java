package com.example.demo.backend;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table (name = "Offer")
@AllArgsConstructor
@NoArgsConstructor
public class Offer {
    @EmbeddedId
    private KeyForOffer keyForOffer;
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "creditID", referencedColumnName = "id", insertable = false, updatable = false)
    private Client client;
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "creditID", referencedColumnName = "id", insertable = false, updatable = false)
    private Credit credit;
    private Integer amount;
}



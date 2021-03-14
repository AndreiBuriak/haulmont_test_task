package com.example.demo.backend;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class KeyForOffer implements Serializable {
    private Integer clientID;
    private Integer creditID;
}
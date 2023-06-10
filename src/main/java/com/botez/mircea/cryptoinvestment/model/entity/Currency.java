package com.botez.mircea.cryptoinvestment.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Currency {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="TIMESTAMP", nullable=false)
    private Long timestamp;

    @Column(name="SYMBOL", nullable=false)
    private String symbol;

    @Column(name="PRICE", nullable=false)
    private Double price;
}

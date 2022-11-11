package com.rore.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Battery extends BaseEntity{

    @Column(length = 50)
    private String name;

    @Column(length = 6)
    private Integer postcode;

    @Column(length = 6)
    private String watt;

}
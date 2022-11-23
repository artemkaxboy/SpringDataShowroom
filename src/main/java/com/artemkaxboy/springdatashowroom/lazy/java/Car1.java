package com.artemkaxboy.springdatashowroom.lazy.java;

import java.util.StringJoiner;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Car1 {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "brand_id")
    private Brand1 brand;

    @Column(name = "brand_id", insertable = false, updatable = false)
    private Long brandId;

    private String model;

    public Car1() {
    }

    public Car1(Long id, Brand1 brand, String model) {
        this.id = id;
        this.brand = brand;
        this.model = model;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car1.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("brandId=" + brandId)
                .add("model='" + model + "'")
                .toString();
    }
}

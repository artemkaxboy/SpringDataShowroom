package com.artemkaxboy.springdatashowroom.lazy.java;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Brand1 {

    @Id
    private Long id;

    private String name;

    public Brand1() {}

    public Brand1(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

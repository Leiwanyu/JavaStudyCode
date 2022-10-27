package com.lendea.java_common_mistakes.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author lendea
 * @date 2022/8/12 14:44
 */
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }
}

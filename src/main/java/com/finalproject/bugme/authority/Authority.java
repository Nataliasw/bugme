package com.finalproject.bugme.authority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finalproject.bugme.person.Person;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;


@Setter
@Getter
@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(unique = true,length = 256)
    private String name;

    @ManyToOne
    @JoinColumn(name="person_id")
    @JsonIgnoreProperties("authorities")
    private Person person;

    public Authority(String name){
        this.name = name;
    }

    public Authority(){

    }
}

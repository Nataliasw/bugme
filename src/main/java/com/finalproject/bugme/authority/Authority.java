package com.finalproject.bugme.authority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finalproject.bugme.allenums.AuthorityName;
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

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private AuthorityName name;



    public Authority(AuthorityName name){
        this.name = name;
    }

    public Authority(){

    }
}

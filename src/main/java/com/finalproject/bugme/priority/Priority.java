package com.finalproject.bugme.priority;

import com.finalproject.bugme.authority.AuthorityName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private PriorityName name;


    public Priority(PriorityName name){
        this.name = name;
    }

    public Priority(){

    }


}

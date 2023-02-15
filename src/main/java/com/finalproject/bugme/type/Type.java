package com.finalproject.bugme.type;

import com.finalproject.bugme.priority.PriorityName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private TypeName name;

    public Type(TypeName name){
        this.name = name;
    }

    public Type(){

    }
}

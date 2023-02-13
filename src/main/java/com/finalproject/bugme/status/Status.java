package com.finalproject.bugme.status;

import com.finalproject.bugme.priority.PriorityName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private StatusName name;

    public Status(StatusName name){
        this.name = name;
    }

    public Status(){}
}

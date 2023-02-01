package com.finalproject.bugme.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finalproject.bugme.issue.Issue;
import com.finalproject.bugme.person.Person;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Entity
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false, length = 255)
    private String name;



    @ManyToOne
    @JoinColumn(name="creator_id")
    @JsonIgnoreProperties("projects")
    private Person creator;


    @Column(nullable = false)
    private boolean enabled;

    @Column(unique = true, nullable = false)
    private Date dateCreated;

    @PrePersist
    protected void onCreate() {
        dateCreated = new Date();
    }
    @Column(columnDefinition="TEXT")
    private String description;

    public Project(String name, String description,Person creator){
        this.name = name;
        this.enabled = true;
        this.description = description;
        this.dateCreated = new Date();
        this.creator = creator;
    }

    public Project(){}

}

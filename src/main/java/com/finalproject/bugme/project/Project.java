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


    @Column(unique = true, nullable = false)
    private boolean enabled;

    @Column(unique = true, nullable = false)
    private Date dateCreated;

    @Column(unique = true, nullable = false, length = 255)
    private String code;

    @Column(columnDefinition="TEXT")
    private String description;

    public Project(String name, boolean enabled,String code, String description){
        this.name = name;
        this.code = code;
        this.enabled = enabled;
        this.description = description;
        this.dateCreated = new Date();
    }

    public Project(){}

}

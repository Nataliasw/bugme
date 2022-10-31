package com.finalproject.bugme.issue;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finalproject.bugme.comment.Comment;
import com.finalproject.bugme.person.Person;
import com.finalproject.bugme.project.Project;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    //Change to enum: status, priority, type;
   private String status;
   private String priority;
    private String type;
    //private List<Enum> tags;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable=false)
    private String code;

    @Column(nullable = false)
    private Date dateCreated;

    @Column(nullable = false)
    private Date lastUpdate;

    @ManyToOne
    @JoinColumn(name="project_id")
    @JsonIgnoreProperties("issues")
    private Project project;



    @OneToMany(mappedBy = "issue")
    @JsonIgnoreProperties("issue")
    private List<Comment> comments;


    @ManyToOne
    @JoinColumn(name="creator_id")
    @JsonIgnoreProperties("issuesCreated")
    private Person creator;

    @ManyToOne
    @JoinColumn(name="assignee_id")
    @JsonIgnoreProperties("issuesAssigned")
    private Person assignee;


    public Issue(String status, String priority, String type, String name, String description, String code, Date dateCreated, Date lastUpdate) {
        this.status = status;
        this.priority = priority;
        this.type = type;
        this.name = name;
        this.description = description;
        this.code = code;
        this.dateCreated = new Date();
        this.lastUpdate = new Date();
    }

    public Issue(){}
}

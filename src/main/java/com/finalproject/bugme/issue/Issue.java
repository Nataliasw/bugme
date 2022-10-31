package com.finalproject.bugme.issue;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finalproject.bugme.allenums.Priority;
import com.finalproject.bugme.allenums.Status;
import com.finalproject.bugme.allenums.Type;
import com.finalproject.bugme.comment.Comment;
import com.finalproject.bugme.person.Person;
import com.finalproject.bugme.project.Project;
import com.finalproject.bugme.tag.Tag;
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


   private Status status;
   private Priority priority;
   private Type type;

    @OneToMany(mappedBy = "issue")
    @JsonIgnoreProperties("issue")
    private List<Tag> tags;

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


    public Issue(Status status, Priority priority, Type type, String name, String description, String code, Date dateCreated, Date lastUpdate) {
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

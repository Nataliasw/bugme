package com.finalproject.bugme.issue;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finalproject.bugme.enums.State;
import com.finalproject.bugme.enums.Priority;
import com.finalproject.bugme.enums.Type;
import com.finalproject.bugme.comment.Comment;
import com.finalproject.bugme.person.Person;
import com.finalproject.bugme.project.Project;
import com.finalproject.bugme.tag.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    State state = State.TODO;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Priority priority = Priority.LOW;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Type type = Type.TASK;

    @OneToMany(mappedBy = "issue")
    @JsonIgnoreProperties("issue")
    private List<Tag> tags;

    @NotEmpty
    @Size(min=2, max=255)
    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;


    @Column(nullable = false)
    private Date dateCreated;

    @PrePersist
    protected void onCreate() {
        dateCreated = new Date();
        lastUpdate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = new Date();

    }

    @Column(nullable = false)
    private Date lastUpdate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties("issues")
    private Project project;


    @OneToMany(mappedBy = "issue")
    @JsonIgnoreProperties("issue")
    private List<Comment> comments;


    @ManyToOne
    @JoinColumn(name = "creator_id")
    @JsonIgnoreProperties("issuesCreated")
    private Person creator;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    @JsonIgnoreProperties("issuesAssigned")
    private Person assignee;


    public Issue(String name, String description) {
        this.name = name;
        this.description = description;
        this.dateCreated = new Date();
        this.lastUpdate = new Date();
    }

    public Issue() {
    }






}

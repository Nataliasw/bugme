package com.finalproject.bugme.issue;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finalproject.bugme.priority.Priority;
import com.finalproject.bugme.status.Status;
import com.finalproject.bugme.status.StatusName;
import com.finalproject.bugme.type.Type;
import com.finalproject.bugme.type.TypeName;
import com.finalproject.bugme.comment.Comment;
import com.finalproject.bugme.person.Person;
import com.finalproject.bugme.project.Project;
import com.finalproject.bugme.tag.Tag;
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


    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "issue_status",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "status_id"))
    private Set<Status> statuses;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "issue_priority",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "priority_id"))
    private Set<Priority> priorities;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "issue_type",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    private Set<Type> types;

    @OneToMany(mappedBy = "issue")
    @JsonIgnoreProperties("issue")
    private List<Tag> tags;

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

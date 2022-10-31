package com.finalproject.bugme.person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finalproject.bugme.authority.Authority;
import com.finalproject.bugme.comment.Comment;
import com.finalproject.bugme.issue.Issue;
import com.finalproject.bugme.project.Project;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false,unique = true,length = 255)
    private String login;

    @Column(nullable = false,length = 255)
    private String password;

    @Column(nullable = false,length = 255)
    private String userRealName;

    @OneToMany(mappedBy="creator")
    @JsonIgnoreProperties("person")
    private Set<Project> projects;

    @OneToMany(mappedBy="author")
    @JsonIgnoreProperties("person")
    private Set<Comment> comments;

    @OneToMany(mappedBy="creator")
    @JsonIgnoreProperties("person")
    private Set<Issue> issuesCreated;

    @OneToMany(mappedBy ="assignee")
    @JsonIgnoreProperties("person")
    private Set<Issue> issuesAssigned;

    @OneToMany(mappedBy="person")
    @JsonIgnoreProperties("person")
    private Set<Authority> authorieties;


    public Person(String login, String password, String userRealName) {
        this.login = login;
        this.password = password;
        this.userRealName = userRealName;
    }

    public Person() {
    }
}

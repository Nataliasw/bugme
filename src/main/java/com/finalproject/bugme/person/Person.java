package com.finalproject.bugme.person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finalproject.bugme.authority.Authority;
import com.finalproject.bugme.comment.Comment;
import com.finalproject.bugme.issue.Issue;
import com.finalproject.bugme.project.Project;
import com.finalproject.bugme.validators.UniqueUsername;
import com.finalproject.bugme.validators.ValidPasswords;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.Set;

@Setter
@NoArgsConstructor
@Getter
@Entity
@UniqueUsername
@ValidPasswords
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @NotEmpty
    @Size(min=5,max=255)
    @Column(nullable = false,unique = true,length = 255)

    private String login;

    @NotEmpty
    @Size(min=8,max=100)
    @Column(nullable = false,length = 255)
    private String password;
    @NotEmpty
    @Email
    @Size(min=3,max=255)
    @Column
    private String email;
    @Transient
    String repeatedPassword;
    @NotEmpty
    @Size(min=3,max=255)
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

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "person_authorities",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authority;


    public Person(String login, String password, String userRealName) {
        this.login = login;
        this.password = password;
        this.userRealName = userRealName;
    }


}

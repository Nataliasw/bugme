package com.finalproject.bugme.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finalproject.bugme.issue.Issue;
import com.finalproject.bugme.person.Person;
import lombok.*;


import jakarta.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Date dateCreated;


    @ManyToOne
    @JoinColumn(name="creator_id")
    @JsonIgnoreProperties("comments")
    private Person author;

    @ManyToOne
    @JoinColumn(name="issue_id")
    @JsonIgnoreProperties("comments")
    private Issue issue;

    @Column(nullable = false, length = 2000)
    private String content;

    public Comment(String content) {
        this.content = content;
        this.dateCreated = new Date();
    }

    public Comment(){}
}

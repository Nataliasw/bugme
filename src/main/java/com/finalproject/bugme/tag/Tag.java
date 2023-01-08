package com.finalproject.bugme.tag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finalproject.bugme.issue.Issue;
import lombok.Getter;
import lombok.Setter;


import jakarta.persistence.*;

@Setter
@Getter
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(unique = true, nullable = false, length = 255)
    private String tagName;

    @ManyToOne
    @JoinColumn(name="issue_id")
    @JsonIgnoreProperties("tags")
    private Issue issue;

    public Tag(String tagName){
        this.tagName = tagName;
    }
    public Tag(){}

}

package com.finalproject.bugme.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue,Long>,JpaSpecificationExecutor<Issue> {



    Issue findByName(String name);

    List<Issue> findAllByProjectId(Long id);


}

package com.finalproject.bugme.issue;

import com.finalproject.bugme.allenums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue,Long>,JpaSpecificationExecutor<Issue> {

    List<Issue> findByType(Type type);

    Issue findByName(String name);



}

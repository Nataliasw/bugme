package com.finalproject.bugme.issue;


import com.finalproject.bugme.person.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;

    public Page<Issue> findAll(IssueFilter filter, Pageable pageable){
        return issueRepository.findAll(filter.buildQuery(),pageable);
    }


    public List<Person> findAllCreators(){
        return issueRepository.findAll().stream().map(Issue::getCreator).collect(Collectors.toList());
    }

    public List<Person> findAllAssignees(){
        return issueRepository.findAll().stream().map(Issue::getAssignee).collect(Collectors.toList());
    }
}

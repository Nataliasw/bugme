package com.finalproject.bugme.issue;


import com.finalproject.bugme.person.Person;
import com.finalproject.bugme.person.PersonRepository;
import com.finalproject.bugme.project.Project;
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
    private final PersonRepository personRepository;

    public Page<Issue> findAll(IssueFilter filter, Pageable pageable){
        return issueRepository.findAll(filter.buildQuery(),pageable);
    }


    public List<Person> findAllCreators(){
        return issueRepository.findAll().stream().map(Issue::getCreator).collect(Collectors.toList());
    }

    public List<Person> findAllAssignees(){
        return issueRepository.findAll().stream().map(Issue::getAssignee).collect(Collectors.toList());
    }

    public void saveIssue(Issue issue){issueRepository.save(issue);}

    public  List<Issue> findAll(){return issueRepository.findAll();}

    public void deleteIssue(Long id){
        issueRepository.deleteById(id);
    }

    public Issue findById(Long id){
        return issueRepository.findById(id).get();
    }


    public List<Issue> findAllByProjectId(Long id){
        return issueRepository.findAllByProjectId(id);
    }

    public Person findCreatorById(Long id){
        return personRepository.findById(id).get();
    }
}

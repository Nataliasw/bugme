package com.finalproject.bugme.project;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.finalproject.bugme.person.Person;
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;


    public Page<Project> findAll(ProjectFilter filter, Pageable pageable) {
        return projectRepository.findAll(filter.buildQuery(), pageable);
    }


    public List<Project> findAllEnabled(){
        return projectRepository.findByEnabledNative(true);
    }

    public List<Person> findAllCreators(){
        return findAllEnabled().stream().map(Project::getCreator).collect(Collectors.toList());
    }
}

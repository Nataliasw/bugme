package com.finalproject.bugme.project;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> findAll(ProjectFilter filter){
        return projectRepository.findAll(filter.buildQuery());
    }


    public List<Project> findAllEnabled(){
        return projectRepository.findByEnabledNative(true);
    }
}

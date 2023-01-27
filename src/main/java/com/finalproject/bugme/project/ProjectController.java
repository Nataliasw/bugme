package com.finalproject.bugme.project;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ModelAndView project(@ModelAttribute ProjectFilter filter, Pageable pageable){
        Page<Project> projects = projectService.findAll(filter,pageable);
        ModelAndView modelAndView = new ModelAndView("projects/index");
        modelAndView.addObject("projects",projects);
        modelAndView.addObject("filter",filter);
        return modelAndView;
    }

}

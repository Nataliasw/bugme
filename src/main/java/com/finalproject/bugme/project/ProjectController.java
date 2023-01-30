package com.finalproject.bugme.project;

import com.finalproject.bugme.person.Person;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Pageable;


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
        modelAndView.addObject("creators",projectService.findAllCreators());
        return modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView create(){
        ModelAndView modelAndView = new ModelAndView("projects/create");
        modelAndView.addObject("project", new Project());
        return modelAndView;
    }

    @PostMapping(value="/save")

    ModelAndView saveProject(@ModelAttribute @Valid Project project){
        ModelAndView modelAndView = new ModelAndView();


        projectService.saveProject(project);
        modelAndView.setViewName("redirect:/people/");
        return modelAndView;
    }

}

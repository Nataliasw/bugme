package com.finalproject.bugme.project;

import com.finalproject.bugme.authentication.AuthenticationInterface;
import com.finalproject.bugme.person.Person;
import com.finalproject.bugme.person.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final AuthenticationInterface authenticationInterface;
    private final PersonService personService;

    @GetMapping()
    public ModelAndView project(@ModelAttribute ProjectFilter filter, Pageable pageable) {
        Page<Project> projects = projectService.findAll(filter, pageable);
        ModelAndView modelAndView = new ModelAndView("projects/index");
        modelAndView.addObject("projects", projects);
        modelAndView.addObject("filter", filter);
        modelAndView.addObject("creators", projectService.findAllCreators());
        return modelAndView;
    }



    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("projects/create");
        modelAndView.addObject("project", new Project());

        return modelAndView;
    }

    @PostMapping(value = "/save")
    ModelAndView saveProject(@ModelAttribute @Valid Project project) {
        ModelAndView modelAndView = new ModelAndView();

        Authentication authentication = authenticationInterface.getAuthentication();
        String login = authentication.getName();
        Person loggedInUser = personService.findByLogin(login);
        project.setCreator(loggedInUser);
        project.setEnabled(true);
        projectService.saveProject(project);
        modelAndView.setViewName("redirect:/projects");
        return modelAndView;
    }

    @GetMapping("/project/{id}")
    ModelAndView getProjectById(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView("projects/project-view");
        Project foundProject =projectService.findById(id);

        modelAndView.addObject("project",foundProject);



        return modelAndView;
    }

    @GetMapping(value="/project_delete/{id}")
    public ModelAndView deleteProject(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();
        projectService.deleteProject(id);
        modelAndView.setViewName("redirect:/projects");
        return  modelAndView;
    }
}

package com.finalproject.bugme.project;

import com.finalproject.bugme.authentication.AuthenticationInterface;
import com.finalproject.bugme.authority.Authority;
import com.finalproject.bugme.issue.Issue;
import com.finalproject.bugme.issue.IssueService;
import com.finalproject.bugme.person.Person;
import com.finalproject.bugme.person.PersonService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final AuthenticationInterface authenticationInterface;
    private final PersonService personService;

    private final IssueService issueService;

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
        List<Issue> issues = issueService.findAllByProjectId(id);
        modelAndView.addObject("issues",issues);
        modelAndView.addObject("project",foundProject);



        return resetPage(modelAndView);
    }

    public ModelAndView resetPage(ModelAndView modelAndView){
        Authentication authentication = authenticationInterface.getAuthentication();
        Person loggedInUser = personService.findByLogin(authentication.getName());
        Boolean hasAuthority = loggedInUser.getAuthority().stream().anyMatch(authority -> authority.getName().toString().equals("ROLE_MANAGE_PROJECT"));
        modelAndView.addObject("hasAuthority",hasAuthority);
        modelAndView.addObject("person",loggedInUser);
        return modelAndView;
    }

    @GetMapping(value="/project_delete/{id}")
    public ModelAndView deleteProject(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();

        Authentication authentication = authenticationInterface.getAuthentication();
        Person loggedInUser = personService.findByLogin(authentication.getName());
        Set<Authority> authorities = loggedInUser.getAuthority();
        if(authorities.stream().anyMatch(authority -> authority.getName().toString().equals("ROLE_MANAGE_PROJECT"))){
            projectService.deleteProject(id);
            return modelAndView;
        }

        if(Objects.equals(projectService.findById(id).getCreator().getId(), loggedInUser.getId())){
            projectService.deleteProject(id);
        }
        modelAndView.setViewName("redirect:/projects");
        return  modelAndView;
    }

    @PostMapping(value = "update")
    public ModelAndView updateIssue(@ModelAttribute @Valid Project project, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("projects/project-view");
            modelAndView.addObject("project", project);
            List<Issue> issues = issueService.findAllByProjectId(project.getId());
            modelAndView.addObject("issues",issues);
            System.out.println(bindingResult.getAllErrors());
            return resetPage(modelAndView);
        }
        Project projectDate = projectService.findById(project.getId());
        project.setDateCreated(projectDate.getDateCreated());
        Authentication authentication = authenticationInterface.getAuthentication();
        String login = authentication.getName();
        Person loggedInUser = personService.findByLogin(login);
        project.setCreator(loggedInUser);

        projectService.saveProject(project);

        modelAndView.setViewName("redirect:/projects");
        return modelAndView;
    }

}

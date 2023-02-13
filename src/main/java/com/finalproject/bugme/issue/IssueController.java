package com.finalproject.bugme.issue;

import com.finalproject.bugme.authentication.AuthenticationInterface;
import com.finalproject.bugme.person.Person;
import com.finalproject.bugme.person.PersonService;
import com.finalproject.bugme.priority.Priority;
import com.finalproject.bugme.priority.PriorityRepository;
import com.finalproject.bugme.project.Project;
import com.finalproject.bugme.project.ProjectService;
import com.finalproject.bugme.status.Status;
import com.finalproject.bugme.status.StatusRepository;
import com.finalproject.bugme.type.Type;
import com.finalproject.bugme.type.TypeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;

import java.util.List;

@Controller
@RequestMapping("/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    private final AuthenticationInterface authenticationInterface;
    private final PersonService personService;
    private final ProjectService projectService;

    private final TypeRepository typeRepository;
    private final StatusRepository statusRepository;
    private final PriorityRepository priorityRepository;


    @GetMapping
    public ModelAndView issues(@ModelAttribute IssueFilter filter, Pageable pageable) {
        Page<Issue> issues = issueService.findAll(filter, pageable);
        ModelAndView modelAndView = new ModelAndView("issues/index");
        modelAndView.addObject("issues", issues);
        modelAndView.addObject("filter", filter);
        modelAndView.addObject("creators", issueService.findAllCreators());
        modelAndView.addObject("assignees", issueService.findAllAssignees());
        return modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("issues/create");
        List<Priority> priorities = priorityRepository.findAll();
        List<Status> statuses = statusRepository.findAll();
        List<Type> types = typeRepository.findAll();
        modelAndView.addObject("priorities", priorities);
        modelAndView.addObject("statuses", statuses);
        modelAndView.addObject("types", types);
        modelAndView.addObject("issue", new Issue());
        modelAndView.addObject("users", personService.findAllUsers());
        modelAndView.addObject("projects", projectService.findAllEnabled());


        return modelAndView;
    }

    @PostMapping(value = "save")
    public ModelAndView saveIssue(@ModelAttribute @Valid Issue issue) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationInterface.getAuthentication();
        String login = authentication.getName();
        Person loggedInUser = personService.findByLogin(login);
        issue.setCreator(loggedInUser);
        issueService.saveIssue(issue);
        modelAndView.setViewName("redirect:/issues");
        return modelAndView;
    }

    @GetMapping("/issue/{id}")
    ModelAndView getIssueById(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("issues/issue-view");
        Issue foundIssue = issueService.findById(id);
        modelAndView.addObject("issue", foundIssue);

        return modelAndView;
    }



    @GetMapping(value="/issue_delete/{id}")
    public ModelAndView deleteIssue(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();
        issueService.deleteIssue(id);
        modelAndView.setViewName("redirect:/issues");
        return  modelAndView;
    }
}

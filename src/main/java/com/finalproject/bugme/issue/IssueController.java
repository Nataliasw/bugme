package com.finalproject.bugme.issue;

import com.finalproject.bugme.authentication.AuthenticationInterface;
import com.finalproject.bugme.enums.State;
import com.finalproject.bugme.mail.Mail;
import com.finalproject.bugme.mail.MailService;
import com.finalproject.bugme.person.Person;
import com.finalproject.bugme.person.PersonService;
import com.finalproject.bugme.project.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    private final AuthenticationInterface authenticationInterface;
    private final PersonService personService;
    private final ProjectService projectService;

    private final MailService mailService;




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
        modelAndView.addObject("issue", new Issue());
        return resetCreatePage(modelAndView);
    }

    private ModelAndView resetCreatePage(ModelAndView modelAndView) {

        modelAndView.addObject("users", personService.findAllUsers());
        modelAndView.addObject("projects", projectService.findAllEnabled());
        return modelAndView;
    }

    @PostMapping(value = "save")
    public ModelAndView saveIssue(@ModelAttribute @Valid Issue issue, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("issues/create");
            modelAndView.addObject("issue", issue);
            System.out.println(bindingResult.getAllErrors());
            return resetCreatePage(modelAndView);
        }

        Authentication authentication = authenticationInterface.getAuthentication();
        String login = authentication.getName();
        Person loggedInUser = personService.findByLogin(login);
        issue.setCreator(loggedInUser);
        Person assignee = issue.getAssignee();
        Mail issueAssigned = new Mail();
        issueAssigned.setContent("New issue " + issue.getName() + " assigned to you by " + issue.getCreator().getUserRealName());
        issueAssigned.setSubject("New issue " + issue.getName() + " assigned");
        issueAssigned.setRecipient(assignee.getEmail());
        issueService.saveIssue(issue);
        mailService.sendMail(issueAssigned);
        modelAndView.setViewName("redirect:/issues");
        return modelAndView;
    }
    @PostMapping(value = "update")
    public ModelAndView updateIssue(@ModelAttribute @Valid Issue issue, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("issues/issue_edit");
            modelAndView.addObject("issue", issue);
            System.out.println(bindingResult.getAllErrors());
            return resetCreatePage(modelAndView);
        }
        Issue issueDate = issueService.findById(issue.getId());
        issue.setDateCreated(issueDate.getDateCreated());
        Authentication authentication = authenticationInterface.getAuthentication();
        String login = authentication.getName();
        Person loggedInUser = personService.findByLogin(login);
        issue.setCreator(loggedInUser);

        issueService.saveIssue(issue);

        modelAndView.setViewName("redirect:/issues");
        return modelAndView;
    }


    @GetMapping("/{id}")
    ModelAndView show(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("issues/issue_edit");

        modelAndView.addObject("issue", issueService.findById(id));
        modelAndView.addObject("projects", projectService.findAllEnabled());
        modelAndView.addObject("people", personService.findAllUsers());

        return modelAndView;
    }


    @GetMapping(value = "/issue_delete/{id}")
    public ModelAndView deleteIssue(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        issueService.deleteIssue(id);
        modelAndView.setViewName("redirect:/issues");
        return modelAndView;
    }

    @PatchMapping("/state/{id}")
    ResponseEntity<Void> updateState(@PathVariable Long id, @RequestBody State state) {

        Issue issue = issueService.findById(id);
        issue.setState(state);
        issueService.saveIssue(issue);

        return ResponseEntity.ok().build();
    }
}


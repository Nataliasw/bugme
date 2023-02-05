package com.finalproject.bugme.issue;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;


    @GetMapping
    public ModelAndView issues(@ModelAttribute IssueFilter filter, Pageable pageable){
        Page<Issue> issues = issueService.findAll(filter,pageable);
        ModelAndView modelAndView = new ModelAndView("issues/index");
        modelAndView.addObject("issues",issues);
        modelAndView.addObject("filter",filter);
        modelAndView.addObject("creators",issueService.findAllCreators());
        modelAndView.addObject("assignees",issueService.findAllAssignees());
        return modelAndView;
        }
    }


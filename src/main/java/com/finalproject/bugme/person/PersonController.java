package com.finalproject.bugme.person;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;


    @GetMapping("/")
    @Secured("ROLE_USERS_TAB")
    ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("people/index");
        modelAndView.addObject("people",personService.findAllUsers());
        return modelAndView;
    }

    @GetMapping("/create")
    @Secured("ROLE_CREATE_USER")
    ModelAndView create(){
        ModelAndView modelAndView = new ModelAndView("people/create");
        modelAndView.addObject("person", new Person());
        return modelAndView;
    }

    @PostMapping(value="save")
    @Secured("ROLE_CREATE_USER")
    ModelAndView saveUser(@ModelAttribute @Valid Person person, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();

        if(bindingResult.hasErrors()){
            modelAndView.setViewName("people/create");
            modelAndView.addObject("person",person);
            return modelAndView;
        }
        personService.savePerson(person);
        modelAndView.setViewName("redirect:/people/");
        return modelAndView;
    }
}

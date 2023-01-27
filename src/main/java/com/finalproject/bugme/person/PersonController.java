package com.finalproject.bugme.person;

import com.finalproject.bugme.authority.Authority;
import com.finalproject.bugme.authority.AuthorityRepository;
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

import java.util.List;

@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final AuthorityRepository authorityRepository;

    private final PersonRepository personRepository;

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
        List<Authority> authorities = authorityRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("people/create");
        modelAndView.addObject("authorities", authorities);
        modelAndView.addObject("person", new Person());
        return modelAndView;
    }

    @PostMapping(value="/save")
    @Secured("ROLE_CREATE_USER")
    ModelAndView saveUser(@ModelAttribute @Valid Person person, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();

        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            modelAndView.setViewName("people/create");
            modelAndView.addObject("authorities",authorityRepository.findAll());
            modelAndView.addObject("person",person);
            return modelAndView;
        }
        personService.savePerson(person);
        modelAndView.setViewName("redirect:/people/");
        return modelAndView;
    }
}

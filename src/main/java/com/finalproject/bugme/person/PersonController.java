package com.finalproject.bugme.person;

import com.finalproject.bugme.authentication.AuthenticationInterface;
import com.finalproject.bugme.authority.Authority;
import com.finalproject.bugme.authority.AuthorityRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final AuthorityRepository authorityRepository;

    private final AuthenticationInterface authenticationInterface;

    @GetMapping("/")
    @Secured("ROLE_USERS_TAB")
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("people/index");
        modelAndView.addObject("people", personService.findAllUsers());
        return modelAndView;
    }

    @GetMapping("/create")
    @Secured("ROLE_CREATE_USER")
    ModelAndView create() {
        List<Authority> authorities = authorityRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("people/create");
        modelAndView.addObject("authorities", authorities);
        modelAndView.addObject("person", new Person());
        return modelAndView;
    }

    @PostMapping
    String save(@ModelAttribute Person person) {
         personService.savePerson(person);

        return "redirect:/people/users/profile";
    }


    @PostMapping(value = "/save")
    @Secured("ROLE_CREATE_USER")
    ModelAndView saveUser(@ModelAttribute @Valid Person person, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            modelAndView.setViewName("people/create");
            modelAndView.addObject("authorities", authorityRepository.findAll());
            modelAndView.addObject("person", person);
            return modelAndView;
        }
        personService.savePerson(person);
        modelAndView.setViewName("redirect:/people/");
        return modelAndView;
    }

    @GetMapping("/users/profile")
    ModelAndView getProfile() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationInterface.getAuthentication();
        String login = authentication.getName();
        Person loggedInUser = personService.findByLogin(login);
        modelAndView.addObject("person", loggedInUser);
        Boolean hasAuthorityCreate = loggedInUser.getAuthority().stream().anyMatch(authority -> authority.getName().toString().equals("ROLE_CREATE_USER"));
        Boolean hasAuthoritySee = loggedInUser.getAuthority().stream().anyMatch(authority -> authority.getName().toString().equals("ROLE_USERS_TAB"));
        modelAndView.addObject("hasAuthSee",hasAuthoritySee);
        modelAndView.addObject("hasAuthCreate",hasAuthorityCreate);
        modelAndView.setViewName("/people/profile");
        return modelAndView;
    }

    @GetMapping(value = "/person_delete/{id}")
    public ModelAndView deletePerson(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();

        personService.deleteUser(id);
        modelAndView.setViewName("redirect:/people/");
        return modelAndView;
    }

    @GetMapping(value="/changepassword")
    public ModelAndView changePass(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/people/changepassword");
        Authentication authentication = authenticationInterface.getAuthentication();
        String login = authentication.getName();
        Person personLoggedIn = personService.findByLogin(login);
        modelAndView.addObject("person",personLoggedIn);

return modelAndView;

    }

    @PostMapping("/passwordchanged")
    public ModelAndView changed(@ModelAttribute Person person){
        ModelAndView modelAndView = new ModelAndView();
        personService.changePass(person);
        modelAndView.setViewName("redirect:/people/");
        return modelAndView;
    }
}

package com.finalproject.bugme.config;

import com.finalproject.bugme.authority.Authority;
import com.finalproject.bugme.person.Person;
import com.finalproject.bugme.person.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

    private final PersonRepository personRepository;

    public CustomUserDetailsService(PersonRepository personRepository){
        this.personRepository= personRepository;
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{
        Person person = personRepository.findByLogin(login);
        System.out.println("login " + login);
        System.out.println("Znaleziony użytkownik: " + person);
        if(person == null){
            throw new UsernameNotFoundException(login);
        }

        return buildUserDetails(person);

    }

    private UserDetails buildUserDetails(Person person) {
        List<GrantedAuthority> authorities = getUserAuthorities(person);
        return new User(person.getLogin(), person.getPassword(), authorities);
    }

    private List<GrantedAuthority> getUserAuthorities(Person person){

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for(Authority authority: person.getAuthority()) {
        grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName().toString()));
        }
        return new ArrayList<>(grantedAuthorities);
    }
}

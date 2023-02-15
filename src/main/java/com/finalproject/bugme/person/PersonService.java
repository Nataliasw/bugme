package com.finalproject.bugme.person;

import com.finalproject.bugme.authority.Authority;
import com.finalproject.bugme.authority.AuthorityRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class PersonService {

    private final AuthorityRepository authorityRepository;
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${my.admin.login}")
    private String myAdminLogin;

    @Value("${my.admin.password}")
    private String myAdminPassword;

    public PersonService(AuthorityRepository authorityRepository, PersonRepository personRepository,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.authorityRepository = authorityRepository;
        this.personRepository = personRepository;
        this.bCryptPasswordEncoder= bCryptPasswordEncoder;
    }

    public void prepareAdminUser(){
        if(personRepository.findByLogin(myAdminLogin) != null){
            System.out.println("Użytkownik " + myAdminLogin + " już istnieje. Przerywamy tworzenie.");
            return;
        }
        Person person = new Person(myAdminLogin,myAdminPassword,"Administrator");
        List<Authority> authorities = authorityRepository.findAll();
        person.setAuthority(new HashSet<>(authorities));
        savePerson(person);
    }

    protected void savePerson(Person person) {
        String hashedPassword = bCryptPasswordEncoder.encode(person.getPassword());
        person.setPassword(hashedPassword);

        personRepository.save(person);
    }

    public List<Person> findAllUsers() {
        return personRepository.findAll();
    }

    public Person findByLogin(String login){
        return personRepository.findByLogin(login);
    }
}


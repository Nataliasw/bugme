package com.finalproject.bugme.config;

import com.finalproject.bugme.authority.AuthorityName;
import com.finalproject.bugme.authority.Authority;
import com.finalproject.bugme.authority.AuthorityRepository;
import com.finalproject.bugme.person.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Bootstrap implements InitializingBean {
    private final AuthorityRepository authorityRepository;

    private final PersonService personService;




    @Override
    public void afterPropertiesSet() {
        System.out.println("Rozpoczynamy przygotowywanie aplikacji...");
        prepareAuthorities();
        personService.prepareAdminUser();

    }

    private void prepareAuthorities(){
        for (AuthorityName name : AuthorityName.values()) {
            Authority existingAuthority = authorityRepository.findByName(name);
            if (existingAuthority == null) {
                Authority authority = new Authority(name);
                authorityRepository.save(authority);
            }
        }
    }

}

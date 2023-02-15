package com.finalproject.bugme.config;

import com.finalproject.bugme.authority.AuthorityName;
import com.finalproject.bugme.authority.Authority;
import com.finalproject.bugme.authority.AuthorityRepository;
import com.finalproject.bugme.person.PersonService;
import com.finalproject.bugme.priority.Priority;
import com.finalproject.bugme.priority.PriorityName;
import com.finalproject.bugme.priority.PriorityRepository;
import com.finalproject.bugme.status.Status;
import com.finalproject.bugme.status.StatusName;
import com.finalproject.bugme.status.StatusRepository;
import com.finalproject.bugme.type.TypeRepository;
import com.finalproject.bugme.type.Type;
import com.finalproject.bugme.type.TypeName;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Bootstrap implements InitializingBean {
    private final AuthorityRepository authorityRepository;
    private final PriorityRepository priorityRepository;

    private final StatusRepository statusRepository;
    private final PersonService personService;

    private final TypeRepository typeRepository;


    @Override
    public void afterPropertiesSet() {
        System.out.println("Rozpoczynamy przygotowywanie aplikacji...");
        prepareAuthorities();
        preparePriorities();
        prepareStatus();
        prepareTypes();
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

    private void preparePriorities(){
        for (PriorityName name : PriorityName.values()) {
            Priority existingPriority = priorityRepository.findByName(name);
            if (existingPriority == null) {
                Priority priority = new Priority(name);
                priorityRepository.save(priority);
            }
        }
    }

    private void prepareStatus(){
        for (StatusName name : StatusName.values()) {
            Status existingStatus = statusRepository.findByName(name);
            if (existingStatus == null) {
                Status status = new Status(name);
                statusRepository.save(status);
            }
        }
    }

    private void prepareTypes(){
        for (TypeName name : TypeName.values()) {
            Type existingType = typeRepository.findByName(name);
            if (existingType == null) {
                Type type = new Type(name);
                typeRepository.save(type);
            }
        }
    }
}

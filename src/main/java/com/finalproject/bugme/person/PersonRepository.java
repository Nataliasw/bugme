package com.finalproject.bugme.person;

import com.finalproject.bugme.authority.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonRepository extends JpaRepository<Person,Long>, JpaSpecificationExecutor<Authority> {
    Person findByLogin(String username);
}

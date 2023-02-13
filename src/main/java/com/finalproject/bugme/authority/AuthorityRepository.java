package com.finalproject.bugme.authority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuthorityRepository extends JpaRepository<Authority,Long>, JpaSpecificationExecutor<Authority> {
    Authority findByName(AuthorityName name);
}

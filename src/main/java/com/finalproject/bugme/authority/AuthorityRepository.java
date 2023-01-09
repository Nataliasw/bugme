package com.finalproject.bugme.authority;

import com.finalproject.bugme.allenums.AuthorityName;
import com.finalproject.bugme.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuthorityRepository extends JpaRepository<Authority,Long>, JpaSpecificationExecutor<Authority> {
    Authority findByName(AuthorityName name);
}

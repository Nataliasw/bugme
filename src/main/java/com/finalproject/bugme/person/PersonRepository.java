package com.finalproject.bugme.person;

import com.finalproject.bugme.authority.Authority;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person,Long>, JpaSpecificationExecutor<Authority> {
    Person findByLogin(String username);

    @Modifying
    @Transactional
    @Query("update Person u set u.password = :password where u.id=:id")
    void updatePassword(@Param(value="id") long id, @Param(value="password") String password);
}

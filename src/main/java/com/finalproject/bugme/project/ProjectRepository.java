package com.finalproject.bugme.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> , JpaSpecificationExecutor<Project> {

    @Query(value = "select * from project where enabled=:enabled",nativeQuery = true)
    List<Project> findByEnabledNative(@Param("enabled") Boolean enabled);

    List<Project> findAllByEnabled(Boolean enabled);



}

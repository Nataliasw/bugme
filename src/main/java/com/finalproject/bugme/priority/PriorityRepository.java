package com.finalproject.bugme.priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface PriorityRepository extends JpaRepository<Priority,Long>, JpaSpecificationExecutor<Priority>{

    Priority findByName(PriorityName name);
}

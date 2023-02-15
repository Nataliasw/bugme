package com.finalproject.bugme.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TypeRepository extends JpaRepository<Type,Long>, JpaSpecificationExecutor<Type> {

    Type findByName(TypeName name);
}

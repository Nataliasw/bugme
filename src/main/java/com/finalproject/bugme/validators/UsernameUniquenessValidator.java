package com.finalproject.bugme.validators;

import com.finalproject.bugme.person.Person;
import com.finalproject.bugme.person.PersonRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@AllArgsConstructor
public class UsernameUniquenessValidator  implements ConstraintValidator<UniqueUsername, Person> {

    private final PersonRepository personRepository;

    @Override
    public void initialize(UniqueUsername constraintAnnotation){
        ConstraintValidator.super.initialize(constraintAnnotation);

    }

    @Override
    public boolean isValid(Person person, ConstraintValidatorContext ctx){

        Person foundPerson = personRepository.findByLogin(person.getLogin());

        if(foundPerson == null){
            return true;
        }

        boolean usernameIsUnique = person.getId() != null && foundPerson.getId().equals(person.getId());

        if(!usernameIsUnique){
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate()).addPropertyNode("login").addConstraintViolation();
        }

        return usernameIsUnique;
    }
}

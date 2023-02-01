package com.finalproject.bugme.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

public interface AuthenticationInterface {

    Authentication getAuthentication();

    @Component
    public class AuthenticationFacade implements AuthenticationInterface{
        @Override
        public Authentication getAuthentication(){
            return SecurityContextHolder.getContext().getAuthentication();
        }
    }
}

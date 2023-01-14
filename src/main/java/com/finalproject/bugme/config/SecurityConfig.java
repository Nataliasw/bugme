package com.finalproject.bugme.config;

import com.finalproject.bugme.person.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final PersonRepository personRepository;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService,PersonRepository personRepository){
        this.customUserDetailsService=customUserDetailsService;
        this.personRepository = personRepository;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/contact").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().permitAll();
        return httpSecurity.build();

    }
    @Bean
    UserDetailsService users() {
        return new CustomUserDetailsService(personRepository);
    }

//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(){
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        UserDetails user = User.withUsername("demo")
//                .password(encoder.encode("secret"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//
//    }



    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder());

        provider.setUserDetailsService(customUserDetailsService);
        return provider;
    }
}

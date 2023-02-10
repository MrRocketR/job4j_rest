package ru.job4j.rest.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.rest.domain.Person;

import static java.util.Collections.emptyList;
@Service
public class PersonDetailsService implements UserDetailsService {
    private final PersonService personService;

    public PersonDetailsService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Person person = personService.findByLogin(login).orElseThrow(
                () -> new UsernameNotFoundException(login)
        );
        return new User(person.getLogin(), person.getPassword(), emptyList());
    }
}
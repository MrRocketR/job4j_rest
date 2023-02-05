package ru.job4j.rest.service;

import org.springframework.stereotype.Service;
import ru.job4j.rest.repository.PersonRepository;
@Service
public class PersonService {
        private final PersonRepository repo;

    public PersonService(PersonRepository repo) {
        this.repo = repo;
    }
}

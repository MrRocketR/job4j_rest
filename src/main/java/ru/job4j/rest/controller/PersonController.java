package ru.job4j.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.rest.model.Person;
import ru.job4j.rest.service.PersonService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private final PersonService personService;
    private BCryptPasswordEncoder encoder;

    public PersonController(PersonService personService,
                            BCryptPasswordEncoder encoder) {
        this.personService = personService;
        this.encoder = encoder;
    }

    /***

     @GetMapping("/all") public List<Person> findAll() {
     return personService.findAll();
     }
     ***/


    @GetMapping("/all")
    public ResponseEntity findAll() {
        List<Person> persons = personService.findAll();
        Map<Integer, Person> body = new HashMap<>();
        for (Person p : persons) {
            body.put(p.getId(), p);
        }
        var entity = new ResponseEntity(
                body,
                HttpStatus.OK
        );
        return entity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.personService.findById(id);
        return new ResponseEntity<>(person.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Person is not found")), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        Optional<Person> optionalPerson = personService.save(person);
        if (optionalPerson.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No person found for update");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!personService.deleteById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No persons with this id");
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Пароли хешируются и прямом виде не хранятся в базе.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody Person person) {
        var username = person.getLogin();
        var password = person.getPassword();
        if (username == null || password == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Invalid password");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        personService.save(person);
        return ResponseEntity.ok().build();
    }
}

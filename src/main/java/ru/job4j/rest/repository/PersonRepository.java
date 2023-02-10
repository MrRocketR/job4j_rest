package ru.job4j.rest.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.rest.domain.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

    @Override
    List<Person> findAll();

    boolean deleteById(int id);

    Optional<Person> findByLogin(String login);
}
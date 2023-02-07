package ru.job4j.rest.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.rest.domain.Person;

import java.util.List;
@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

    List<Person> findAll();


}

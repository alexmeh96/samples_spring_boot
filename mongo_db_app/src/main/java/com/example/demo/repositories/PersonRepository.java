package com.example.demo.repositories;

import com.example.demo.models.person.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
    Person findPersonByName(String name);
}

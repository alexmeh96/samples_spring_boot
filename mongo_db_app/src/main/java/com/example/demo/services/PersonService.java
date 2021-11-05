package com.example.demo.services;

import com.example.demo.models.person.Account;
import com.example.demo.models.person.Person;
import com.example.demo.models.student.Student;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.PersonRepository;
import com.example.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AccountRepository accountRepository;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void addPerson() {
        Person person1 = new Person();
        person1.setName("person1");

        Person person2 = new Person();
        person2.setName("person2");

        Person person3 = new Person();
        person3.setName("person3");

        personRepository.insert(List.of(person1, person2, person3));
    }

    public void addAccount() {

        Account account1 = new Account();
        account1.setProvider("account1");

        Account account2 = new Account();
        account2.setProvider("account2");

        Account account3 = new Account();
        account3.setProvider("account3");

        List<Account> accounts = accountRepository.saveAll(List.of(account1, account2, account3));

        Person person1 = personRepository.findPersonByName("person1");
        Person person2 = personRepository.findPersonByName("person2");

        person1.getAccounts().add(accounts.get(0));
        person1.getAccounts().add(accounts.get(1));
        person2.getAccounts().add(accounts.get(2));

        personRepository.saveAll(List.of(person1, person2));
    }

}

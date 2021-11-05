package com.example.demo.controllers;

import com.example.demo.models.person.Account;
import com.example.demo.models.person.Person;
import com.example.demo.models.student.Student;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.PersonRepository;
import com.example.demo.services.PersonService;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/persons")
public class PersonController {

    @Autowired
    private PersonService personService;
    

    @GetMapping("get-persons")
    public List<Person> fetchAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("get-accounts")
    public List<Account> fetchAllAccounts() {
        return personService.getAllAccounts();
    }

    @GetMapping("add-person")
    public void addPerson() {
        personService.addPerson();
    }

    @GetMapping("add-account")
    public void addAccount() {
        personService.addAccount();
    }


}

package com.example.demo.models.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class Person {
    @Id
    private String id;
    @Indexed
    private String name;


    @JsonIgnore
    @DBRef(lazy = true)
    private List<Account> accounts = new ArrayList<>();
}


package com.example.demo.services;

import com.example.demo.models.blog.Post;
import com.example.demo.models.student.Address;
import com.example.demo.models.student.Gender;
import com.example.demo.models.student.Student;
import com.example.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addStudent() {
        Address address = new Address(
                "England",
                "London",
                "NE9"
        );

        Student student = new Student(
                "Jamila",
                "Ahmed",
                "user@email.com",
                Gender.FEMALE,
                address,
                List.of("Computer Science"),
                BigDecimal.TEN,
                LocalDateTime.now()
        );

        studentRepository.findStudentByEmail("user@email.com").ifPresentOrElse(s -> {
            System.out.println(student + "already exists");
        }, () -> {
            System.out.println("Inserting student " + student);
            studentRepository.insert(student);
        });
    }

    public Student findByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        Student student = mongoTemplate.findOne(query, Student.class);
        return student;
    }
}

package com.example.demo.controllers;

import com.example.demo.models.student.Student;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> fetchAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("add-student")
    public void addStudent() {
       studentService.addStudent();
    }

    @GetMapping("find-by-email")
    public Student findByEmail() {
        Student student = studentService.findByEmail("user@email.com");
        return student;
    }


}

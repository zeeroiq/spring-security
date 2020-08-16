package com.spring.security.controller;

import com.spring.security.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(new Student(1, "Popeye"),
                                                                   new Student(2, "Mickey"),
                                                                   new Student(3, "Donald"),
                                                                   new Student(4, "Chhota Bheem"));


    @GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId) {
        return STUDENTS.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(">>>>> Student not found"));
    }
}

package com.spring.security.controller;

import com.spring.security.entity.Student;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/management/api/v1/students/")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(new Student(1, "Popeye"),
                                                                   new Student(2, "Mickey"),
                                                                   new Student(3, "Donald"),
                                                                   new Student(4, "Chhota Bheem"));



    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_TRAINEE, ADMIN')")
    public List<Student> listStudents() {
        return STUDENTS;
    }

    @GetMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public Student getStudent(@PathVariable("studentId") Integer studentId) {
        return STUDENTS.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(">>>>> Student not found"));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println(">>>>>>>>>>> REGISTER STUDENT");
        System.out.println(student);
    }

    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void  updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        System.out.println(">>>>>>>>>>> UPDATING STUDENT");
        System.out.println(String.format("%s %s", studentId, student));
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable Integer studentId) {
        System.out.println(">>>>>>>>>>> DELETING STUDENT");
        System.out.println(studentId);
    }
}

package com.codeup.crudspring.controller;

import java.util.List;

import com.codeup.crudspring.model.Course;
import com.codeup.crudspring.repository.CourseRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {
    
    private final CourseRepository repository;

    @GetMapping
    public @ResponseBody List<Course> list() {
        return repository.findAll();
    }

}
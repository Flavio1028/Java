package com.codeup.crudspring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import com.codeup.crudspring.exception.RecordNotFoundException;
import com.codeup.crudspring.model.Course;
import com.codeup.crudspring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public List<Course> list() {
        return repository.findAll();
    }

    public Course findById(@PathVariable("id") @NotNull @Positive Long id) {
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public Course create(@Valid Course course) {
        return repository.save(course);
    }

    public Course update(@NotNull @Positive Long id,
            @Valid Course course) {
        return repository.findById(id).map(recordFound -> {
            recordFound.setName(course.getName());
            recordFound.setCategory(course.getCategory());
            return repository.save(recordFound);
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
    }

}
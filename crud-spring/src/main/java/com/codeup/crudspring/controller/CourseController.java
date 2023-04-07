package com.codeup.crudspring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codeup.crudspring.dto.CourseDTO;
import com.codeup.crudspring.service.CourseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDTO> list() {
        return this.courseService.list();
    }

    @GetMapping("/{id}")
    public CourseDTO findById(@PathVariable("id") @NotNull @Positive Long id) {
        return this.courseService.findById(id);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> create(@RequestBody @Valid CourseDTO courseDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.courseService.create(courseDTO));
    }

    @PutMapping("/{id}")
    public CourseDTO update(@PathVariable("id") @NotNull @Positive Long id,
            @RequestBody @Valid CourseDTO courseDTO) {
        return this.courseService.update(id, courseDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") @NotNull @Positive Long id) {
        this.courseService.delete(id);
    }

}
package com.codeup.crudspring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codeup.crudspring.model.Course;
import com.codeup.crudspring.repository.CourseRepository;

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

	@GetMapping("/{id}")
	public ResponseEntity<Course> findById(@PathVariable("id") Long id) {
		return repository.findById(id).map(recordFound -> ResponseEntity.ok().body(recordFound))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Course> create(@RequestBody Course course) {
		course = repository.save(course);
		return ResponseEntity.status(HttpStatus.CREATED).body(course);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Course> update(@PathVariable("id") Long id, 
	        @RequestBody Course course) {
	    return repository.findById(id).map(recordFound -> {
	        recordFound.setName(course.getName());
	        recordFound.setCategory(course.getCategory());
	        Course updated = repository.save(recordFound);
	        return ResponseEntity.ok().body(updated);
	    }).orElse(ResponseEntity.notFound().build());
	}
	
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        return repository.findById(id).map(recordFound -> {
            repository.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

}
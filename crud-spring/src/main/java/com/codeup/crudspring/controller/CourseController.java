package com.codeup.crudspring.controller;

import com.codeup.crudspring.dto.CourseDTO;
import com.codeup.crudspring.dto.CoursePageDTO;
import com.codeup.crudspring.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public CoursePageDTO list(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                              @RequestParam(defaultValue = "10") @Positive @Max(250) int pageSize) {
        return this.courseService.list(page, pageSize);
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
                            @RequestBody @Valid @NotNull CourseDTO courseDTO) {
        return this.courseService.update(id, courseDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") @NotNull @Positive Long id) {
        this.courseService.delete(id);
    }

}
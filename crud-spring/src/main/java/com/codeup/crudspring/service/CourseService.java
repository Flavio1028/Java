package com.codeup.crudspring.service;

import com.codeup.crudspring.dto.CourseDTO;
import com.codeup.crudspring.dto.CoursePageDTO;
import com.codeup.crudspring.dto.mapper.CourseMapper;
import com.codeup.crudspring.exception.RecordNotFoundException;
import com.codeup.crudspring.model.Course;
import com.codeup.crudspring.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class CourseService {

    private final CourseRepository repository;

    private final CourseMapper courseMapper;

    public CourseService(CourseRepository repository, CourseMapper courseMapper) {
        this.repository = repository;
        this.courseMapper = courseMapper;
    }

    public CoursePageDTO list(@PositiveOrZero int page, @Positive @Max(250) int pageSize) {

        Page<Course> pageCourse = repository.findAll(PageRequest.of(page, pageSize));
        List<CourseDTO> courses = pageCourse.get().map(courseMapper::toDTO).toList();

        return new CoursePageDTO(courses, pageCourse.getTotalElements(), pageCourse.getTotalPages());
    }

    public CourseDTO findById(@NotNull @Positive Long id) {
        return repository.findById(id).map(courseMapper::toDTO).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(@Valid CourseDTO courseDTO) {
        return courseMapper.toDTO(repository.save(courseMapper.toEntity(courseDTO)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO courseDTO) {
        return repository.findById(id)
                .map(recordFound -> {
                    Course course = courseMapper.toEntity(courseDTO);
                    recordFound.setName(courseDTO.name());
                    recordFound.setCategory(courseMapper.convertCategoryValue(courseDTO.category()));
                    recordFound.getLessons().clear();
                    course.getLessons().forEach(lesson -> {
                        lesson.setCourse(recordFound);
                        recordFound.getLessons().add(lesson);
                    });
                    return courseMapper.toDTO(repository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
    }

}
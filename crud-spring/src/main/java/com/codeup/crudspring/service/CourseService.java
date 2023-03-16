package com.codeup.crudspring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import com.codeup.crudspring.dto.CourseDTO;
import com.codeup.crudspring.dto.mapper.CourseMapper;
import com.codeup.crudspring.enums.CategoryEnum;
import com.codeup.crudspring.exception.RecordNotFoundException;
import com.codeup.crudspring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
public class CourseService {

    private final CourseRepository repository;
    
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository repository, CourseMapper courseMapper) {
        this.repository = repository;
        this.courseMapper = courseMapper;
    }

    public List<CourseDTO> list() {
        return repository.findAll().stream()
                .map(courseMapper::toDTO).collect(Collectors.toList());
    }

    public CourseDTO findById(@PathVariable("id") @NotNull @Positive Long id) {
        return repository.findById(id).map(courseMapper::toDTO).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(@Valid CourseDTO courseDTO) {
        return courseMapper.toDTO(repository.save(courseMapper.toEntity(courseDTO)));
    }

    public CourseDTO update(@NotNull @Positive Long id,
            @Valid CourseDTO courseDTO) {
        return repository.findById(id).map(recordFound -> {
            recordFound.setName(courseDTO.name());
            recordFound.setCategory(CategoryEnum.FRONT_END);
            return courseMapper.toDTO(repository.save(recordFound));
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
    }

}
package com.codeup.crudspring.dto.mapper;

import org.springframework.stereotype.Component;

import com.codeup.crudspring.dto.CourseDTO;
import com.codeup.crudspring.enums.CategoryEnum;
import com.codeup.crudspring.model.Course;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }
        return new CourseDTO(course.getId(), course.getName(), "");
    }

    public Course toEntity(CourseDTO courseDTO) {
        Course course = new Course();
        if (courseDTO.Id() != null) {
            course.setId(courseDTO.Id());
        }
        course.setName(courseDTO.name());
        course.setCategory(CategoryEnum.FRONT_END);

        return course;
    }

}
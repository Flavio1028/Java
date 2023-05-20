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
        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(),
                course.getLessons());
    }

    public Course toEntity(CourseDTO courseDTO) {
        Course course = new Course();
        if (courseDTO.Id() != null) {
            course.setId(courseDTO.Id());
        }
        course.setName(courseDTO.name());
        course.setCategory(this.converterCategoryEnumValue(courseDTO.category()));

        return course;
    }

    public CategoryEnum converterCategoryEnumValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Front-end" -> CategoryEnum.FRONT_END;
            case "Back-end" -> CategoryEnum.BACK_END;
            default -> throw new IllegalArgumentException("Categoria inválida: " + value);
        };
    }
    
}
package com.codeup.crudspring.dto;

import com.codeup.crudspring.enums.CategoryEnum;
import com.codeup.crudspring.enums.validation.ValueOfEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record CourseDTO(
        @JsonProperty("_id") Long Id,
        @NotBlank @NotNull String name,
        @NotNull @Length(max = 10) @ValueOfEnum(enumClass = CategoryEnum.class) String category,
        @NotNull @NotEmpty @Valid List<LessonDTO> lessons) {

}
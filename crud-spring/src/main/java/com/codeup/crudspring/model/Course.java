package com.codeup.crudspring.model;

import com.codeup.crudspring.enums.CategoryEnum;
import com.codeup.crudspring.enums.StatusEnum;
import com.codeup.crudspring.enums.converters.CategoryEnumConverter;
import com.codeup.crudspring.enums.converters.StatusEnumConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "cursos")
@SQLDelete(sql = "UPDATE cursos SET status = 'Inativo' WHERE id = ?")
@Where(clause = "status = 'Ativo'")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id")
    private Long id;

    @NotBlank
    @NotNull
    @Length(min = 2, max = 100)
    @Column(name = "nome", length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(name = "categoria", length = 10, nullable = false)
    @Convert(converter = CategoryEnumConverter.class)
    private CategoryEnum category;

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusEnumConverter.class)
    private StatusEnum status = StatusEnum.ACTIVE;

    @NotNull
    @NotEmpty
    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    private List<Lesson> lessons = new ArrayList<>();

}
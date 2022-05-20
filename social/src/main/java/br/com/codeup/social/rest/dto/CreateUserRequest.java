package br.com.codeup.social.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserRequest {

    @NotBlank
    private String name;
    @NotNull
    private Integer age;

}

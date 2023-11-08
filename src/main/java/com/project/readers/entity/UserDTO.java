package com.project.readers.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Integer idNum;
    @NotBlank
    @Size(min = 1, max = 18)
    private String id;
    @NotBlank
    @Size(min = 1, max = 128)
    private String pw;
    @NotBlank
    @Size(min = 1, max = 20)
    private String name;
    @NotBlank
    @Size(min = 1, max = 100)
    @Email
    private String email;
}

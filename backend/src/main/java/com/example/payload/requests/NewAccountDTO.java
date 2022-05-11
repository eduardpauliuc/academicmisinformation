package com.example.payload.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewAccountDTO {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    private String role;

    @NotBlank
    private String password;

}

package com.example.payload.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileInformationDTO {

    private Long id;

    private String username;

    private String email;

    private String role;

    private String firstName;

    private String lastName;

    private String birthDate;

    private String newPassword;

}

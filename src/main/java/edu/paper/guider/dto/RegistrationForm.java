package edu.paper.guider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationForm {
    private String username;
    private String password;

    @Email
    private String email;
}

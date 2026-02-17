package org.acme.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/*public class UserDTO {
    @NotBlank(message = "firstName must not be blank")
    public String firstName;

    @NotBlank(message = "lastName must not be blank")
    public String lastName;
    
    @NotBlank(message = "email must not be blank")
    @Email(message = "email must be a valid email address")
    public String email;
}*/
public record UserDTO(
        @NotBlank(message = "firstName must not be blank") String firstName,
        @NotBlank(message = "lastName must not be blank") String lastName,
        @NotBlank(message = "email must not be blank") @Email(message = "email must be a valid email address") String email) {
}
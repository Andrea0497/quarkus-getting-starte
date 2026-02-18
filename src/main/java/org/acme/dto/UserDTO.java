package org.acme.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank(message = "firstName must not be blank") String firstName,
        @NotBlank(message = "lastName must not be blank") String lastName,
        @NotBlank(message = "email must not be blank") @Email(message = "email must be a valid email address") String email,
        Set<RoleDTO> roles) {
}
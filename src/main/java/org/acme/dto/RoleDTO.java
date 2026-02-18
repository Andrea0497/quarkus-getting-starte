package org.acme.dto;

import jakarta.validation.constraints.NotBlank;

public record RoleDTO(@NotBlank(message = "Role description must not be blank") String description) {
}
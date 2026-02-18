package org.acme.dto;

import java.util.Set;

public record UserDTO(String firstName, String lastName, String email, Set<RoleDTO> roles) {
}
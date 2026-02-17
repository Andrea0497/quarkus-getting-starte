package org.acme.pojo;

import org.acme.dto.UserDTO;

public record UserCreatedEvent(UserDTO userDTO, String message) {
}
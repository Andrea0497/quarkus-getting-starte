package org.acme.pojo;

import org.acme.dto.UserWRDTO;

public record UserCreatedEvent(UserWRDTO userWRDTO, String message) {
}
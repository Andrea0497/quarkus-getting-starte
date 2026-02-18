package org.acme.pojo;

import org.acme.dto.UserWoRDTO;

public record UserCreatedEvent(UserWoRDTO userWoRDTO, String message) {
}
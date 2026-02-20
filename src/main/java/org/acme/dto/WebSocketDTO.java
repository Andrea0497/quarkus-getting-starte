package org.acme.dto;

public record WebSocketDTO<T>(T data, String message) {
}
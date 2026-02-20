package org.acme.dto;

public record WebSocketRecord<T>(T data, String message) {
}
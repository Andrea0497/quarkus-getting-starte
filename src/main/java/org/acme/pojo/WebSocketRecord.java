package org.acme.pojo;

public record WebSocketRecord<T>(T data, String message) {
}
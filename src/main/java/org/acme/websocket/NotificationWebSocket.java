package org.acme.websocket;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.acme.pojo.UserCreatedEvent;

import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;

@WebSocket(path = "/notifications")
public class NotificationWebSocket {
    private static final List<WebSocketConnection> SESSIONS = new CopyOnWriteArrayList<>();

    @OnOpen
    public void onOpen(WebSocketConnection connection) {
        SESSIONS.add(connection);
    }

    @OnClose
    public void onClose(WebSocketConnection connection) {
        SESSIONS.remove(connection);
    }

    public void onUserCreated(@Observes(during = TransactionPhase.AFTER_SUCCESS) UserCreatedEvent event) {
        SESSIONS.forEach(c -> c.sendTextAndAwait(event));
    }
}
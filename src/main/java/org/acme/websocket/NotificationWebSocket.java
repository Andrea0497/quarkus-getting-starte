package org.acme.websocket;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.acme.dto.WebSocketRecord;
import org.acme.model.User;

import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.PathParam;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.transaction.Transactional;

@WebSocket(path = "/notifications/{ID}")
public class NotificationWebSocket {
    private static final String ADMIN_ROLE = "admin";
    private static final Map<String, WebSocketConnection> SESSIONS = new ConcurrentHashMap<>();
    private static final List<WebSocketConnection> ADMIN_SESSIONS = new CopyOnWriteArrayList<>();

    @OnOpen
    @ActivateRequestContext
    @Transactional
    public void onOpen(WebSocketConnection connection, @PathParam("ID") String id) {
        try {
            SESSIONS.put(id, connection);
            User.findByIdOptional(Long.valueOf(id)).ifPresent(u -> {
                User user = (User) u;
                if (user.roles.stream().anyMatch(r -> ADMIN_ROLE.equals(r.description))) {
                    ADMIN_SESSIONS.add(connection);
                }
            });
        } catch (NumberFormatException e) {
            connection.closeAndAwait(new CloseReason(4000, "Invalid User ID format"));
        }
    }

    @OnClose
    public void onClose(WebSocketConnection connection, @PathParam("ID") String id) {
        SESSIONS.remove(id);
        ADMIN_SESSIONS.remove(connection);
    }

    public void sendNotification(@Observes(during = TransactionPhase.AFTER_SUCCESS) WebSocketRecord<?> message) {
        ADMIN_SESSIONS.forEach(c -> c.sendTextAndAwait(message));
    }
}
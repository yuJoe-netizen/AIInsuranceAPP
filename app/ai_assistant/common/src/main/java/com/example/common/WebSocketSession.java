package com.example.common;

import jakarta.websocket.Session;
import lombok.Data;

@Data
public class WebSocketSession {

    private String userId;
    private Integer role;
    private Session session;
}

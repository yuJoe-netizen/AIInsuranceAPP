package com.example.common;

import lombok.Data;

import javax.websocket.Session;

@Data
public class WebSocketSession {

    private String userId;
    private Integer role;
    private Session session;
}

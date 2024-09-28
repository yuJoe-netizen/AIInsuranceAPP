package com.example.api.model;

import lombok.Data;

@Data
public class CallMessage {
    private Integer role;
    private String channelType;
    private String text;
}

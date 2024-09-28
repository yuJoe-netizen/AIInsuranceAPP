package com.example.api.ai.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PromptDTO implements Serializable {

    private String title;
    private String content;
    private String lawTitle;
    private String lawContent;
}

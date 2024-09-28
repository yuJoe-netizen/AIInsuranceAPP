package com.example.api.ai.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PromptListDTO implements Serializable {
    private String questionPoint;
    private List<PromptDTO> prompt;
}

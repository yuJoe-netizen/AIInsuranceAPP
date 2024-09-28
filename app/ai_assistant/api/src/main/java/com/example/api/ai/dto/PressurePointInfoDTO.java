package com.example.api.ai.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PressurePointInfoDTO implements Serializable {
    private String pressurePoint;
    private List<PromptListDTO> promptList;
}

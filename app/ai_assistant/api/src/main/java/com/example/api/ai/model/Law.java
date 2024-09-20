package com.example.api.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class Law implements Serializable {
    /**
     * 标题
     */
    private String lawTitle;
    /**
     * 描述
     */
    private String description;
}

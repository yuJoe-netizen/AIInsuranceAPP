package com.example.api.ai.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PromptListReqDTO implements Serializable {
    /**
     * 施压点
     */
    private String pressurePoint;
    /**
     * 问题点关键词
     */
    private String questionPointKeyWords;
}

package com.example.api.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AiSuggestion implements Serializable {
    private String question;
    private List<Suggestion> suggestions;
}

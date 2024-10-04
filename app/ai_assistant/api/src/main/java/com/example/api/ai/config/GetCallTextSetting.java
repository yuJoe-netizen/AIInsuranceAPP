package com.example.api.ai.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class GetCallTextSetting {

    @Value("${ai.agent.text.output.type}")
    private Integer sendMessageSwitch;
}

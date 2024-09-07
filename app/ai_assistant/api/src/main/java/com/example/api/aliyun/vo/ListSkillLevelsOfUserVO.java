package com.example.api.aliyun.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ListSkillLevelsOfUserVO {

    private String instanceId;
    @JsonProperty("pageNumber")
    private String PageNumber;
    @JsonProperty("pageSize")
    private String PageSize;
}

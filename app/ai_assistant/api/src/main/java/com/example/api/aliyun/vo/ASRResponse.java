package com.example.api.aliyun.vo;

import com.example.common.enums.IsSuccessEnum;
import lombok.Data;

@Data
public class ASRResponse {

    private String result;
    private IsSuccessEnum isSuccess;
    private String error;
}

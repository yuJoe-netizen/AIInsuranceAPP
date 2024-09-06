package com.example.api.aliyun.controller;


import com.example.api.aliyun.CCCUtil;
import com.example.api.aliyun.vo.ALiYunApiReqVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/aliyun/ccc")
@AllArgsConstructor
public class AliYunController {
    private final CCCUtil cccUtil;


    @PostMapping(value = "/api")
    public Object api(ALiYunApiReqVO request) throws ExecutionException, InterruptedException {
//        log.info("action:{}",action);
        log.info("request:{}",request);
        if (Objects.equals("GetTurnServerList",request.getAction())){
            return cccUtil.getTurnServerList();
        } else if (Objects.equals("ListSkillLevelsOfUser",request.getAction())) {
            return cccUtil.listSkillLevelsOfUser();
        } else if (Objects.equals("getLoginDetails",request.getAction())) {
            return cccUtil.getLoginDetails(request.getRequest());
        }else if (Objects.equals("ListOutboundNumbersOfUser",request.getAction())) {
            return cccUtil.listOutboundNumbersOfUser(request.getRequest());
        }else if (Objects.equals("ListPrivilegesOfUser",request.getAction())) {
            return cccUtil.listPrivilegesOfUser(request.getRequest());
        }

        return null;
    }
}

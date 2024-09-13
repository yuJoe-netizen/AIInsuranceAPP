package com.example.api.aliyun.controller;


import cn.hutool.json.JSONUtil;
import com.example.api.aliyun.CCCUtil;
import com.example.api.aliyun.vo.ALiYunApiReqVO;
import com.example.api.aliyun.vo.GetTurnServerListVO;
import com.example.api.aliyun.vo.GetUserVO;
import com.example.api.aliyun.vo.ListSkillLevelsOfUserVO;
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
            return cccUtil.getTurnServerList(request.getRequest());
        } else if (Objects.equals("ListSkillLevelsOfUser",request.getAction())) {
            return cccUtil.listSkillLevelsOfUser(request.getRequest());
        } else if (Objects.equals("GetLoginDetails",request.getAction())) {
            return cccUtil.getLoginDetails(request.getRequest());
        }else if (Objects.equals("ListOutboundNumbersOfUser",request.getAction())) {
            return cccUtil.listOutboundNumbersOfUser(request.getRequest());
        }else if (Objects.equals("ListPrivilegesOfUser",request.getAction())) {
            return cccUtil.listPrivilegesOfUser(request.getRequest());
        }else if (Objects.equals("SaveTerminalLog",request.getAction())) {
            return cccUtil.saveTerminalLog(request.getRequest());
        }else if (Objects.equals("ListConfigItems",request.getAction())) {
            return cccUtil.listConfigItems(request.getRequest());
        }else if (Objects.equals("GetTurnCredentials",request.getAction())) {
            return cccUtil.getTurnCredentials(request.getRequest());
        }else if (Objects.equals("ListDevices",request.getAction())) {
            return cccUtil.listDevices(request.getRequest());
        }else if (Objects.equals("ResetAgentState",request.getAction())) {
            return cccUtil.resetAgentState(request.getRequest());
        }else if (Objects.equals("SignInGroup",request.getAction())) {
            return cccUtil.signInGroup(request.getRequest());
        }else if (Objects.equals("PollUserStatus",request.getAction())) {
            return cccUtil.pollUserStatus(request.getRequest());
        }else if (Objects.equals("ReadyForService",request.getAction())) {
            return cccUtil.readyForService(request.getRequest());
        }else if (Objects.equals("TakeBreak",request.getAction())) {
            return cccUtil.takeBreak(request.getRequest());
        }else if (Objects.equals("SignOutGroup",request.getAction())) {
            return cccUtil.signOutGroup(request.getRequest());
        }else if (Objects.equals("PickOutboundNumbers",request.getAction())) {
            return cccUtil.pickOutboundNumbers(request.getRequest());
        }else if (Objects.equals("MakeCall",request.getAction())) {
            return cccUtil.makeCall(request.getRequest());
        }else if (Objects.equals("GetNumberLocation",request.getAction())) {
            return cccUtil.getNumberLocation(request.getRequest());
        }else if (Objects.equals("SaveWebRtcInfo",request.getAction())) {
            return cccUtil.saveWebRtcInfo(request.getRequest());
        }else if (Objects.equals("ReleaseCall",request.getAction())) {
            return cccUtil.releaseCall(request.getRequest());
        }
        return null;
    }


    @PostMapping("/getTurnServerList")
    public Object getTurnServerList(@RequestBody GetTurnServerListVO req) throws ExecutionException, InterruptedException {
        log.info("调用后台接口查询获取云呼提供的turn服务接入点");
        return cccUtil.getTurnServerList(JSONUtil.toJsonStr(req));
    }

    @PostMapping("/listSkillLevelsOfUser")
    public Object listSkillLevelsOfUser(@RequestBody ListSkillLevelsOfUserVO req) throws ExecutionException, InterruptedException {
        log.info("调用后台接口查询坐席的技能组信息列表");
        return cccUtil.listSkillLevelsOfUser(JSONUtil.toJsonStr(req));
    }

    @PostMapping("/getUser")
    public Object getUser(@RequestBody GetUserVO req) throws ExecutionException, InterruptedException {
        log.info("调用后台接口查询获取坐席的信息");
        return cccUtil.getUser(JSONUtil.toJsonStr(req));
    }

    @PostMapping("/getLoginDetails")
    public Object getLoginDetails(@RequestBody GetUserVO req) throws ExecutionException, InterruptedException {
        log.info("调用后台接口查询获取登录坐席的信息");
        return cccUtil.getLoginDetails(JSONUtil.toJsonStr(req));
    }
}

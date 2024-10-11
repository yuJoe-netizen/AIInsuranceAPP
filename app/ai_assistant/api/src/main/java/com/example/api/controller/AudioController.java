package com.example.api.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.api.aliyun.utils.ASRUtil;
import com.example.api.aliyun.vo.ASRResponse;
import com.example.common.RespVO;
import com.example.common.enums.IsSuccessEnum;
import com.example.common.enums.YesOrNoEnum;
import com.example.db.entity.AudioText;
import com.example.db.entity.CallAudio;
import com.example.db.mapper.AudioTextMapper;
import com.example.db.mapper.CallAudioMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/audio")
@AllArgsConstructor
@Slf4j
public class AudioController {

    private final CallAudioMapper callAudioMapper;
    private final AudioTextMapper audioTextMapper;


    @RequestMapping("/startParse")
    public RespVO<Void> startParse() {

        QueryWrapper<CallAudio> last = new QueryWrapper<CallAudio>().eq("is_ai_parsed",YesOrNoEnum.NO.getCode())
                .or().eq("is_success","0");
        List<CallAudio> callAudios = callAudioMapper.selectList(last);
        for (CallAudio callAudio : callAudios) {
            ASRResponse response = new ASRResponse();
            ASRUtil.aiParseAudio(callAudio.getFileLink(), response);
            callAudio.setIsAiParsed(response.getIsSuccess() == IsSuccessEnum.SUCCESS ? YesOrNoEnum.YES.getCode() : YesOrNoEnum.NO.getCode());
            callAudio.setIsSuccess(response.getIsSuccess().getCode());
            callAudio.setFailReason(response.getError());
            callAudio.setIsAiParsed(YesOrNoEnum.YES.getCode());
            callAudioMapper.updateById(callAudio);

            List<AudioText> audioTexts = new ArrayList<>();
            if (response.getIsSuccess()==IsSuccessEnum.SUCCESS){
                JSONObject data = JSONUtil.parseObj(response.getResult());
                JSONArray sentences = data.getJSONArray("Sentences");
                if (sentences==null){
                    continue;
                }
                for (Object sentence : sentences) {
                    JSONObject st = (JSONObject) sentence;

                    AudioText audioText = new AudioText();
                    audioText.setContent(st.getStr("Text"));
                    audioText.setRoleId(String.valueOf(st.getInt("ChannelId")));
                    audioText.setStartTime(BigInteger.valueOf(st.getLong("BeginTime")));
                    audioText.setEndTime(BigInteger.valueOf(st.getLong("EndTime")));
                    audioText.setCallId(callAudio.getId());
                    audioTexts.add(audioText);
                }
                audioTextMapper.insert(audioTexts);



            }
        }
        log.info("录音文件解析完毕。。。。");
        return RespVO.success(null);
    }
}

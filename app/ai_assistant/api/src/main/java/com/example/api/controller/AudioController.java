package com.example.api.controller;

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

        QueryWrapper<CallAudio> last = new QueryWrapper<CallAudio>().eq("is_ai_parsed",YesOrNoEnum.NO.getCode()).last("limit 10");
        List<CallAudio> callAudios = callAudioMapper.selectList(last);
//        List<AudioText> audioTexts = new ArrayList<>();
        for (CallAudio callAudio : callAudios) {
            ASRResponse response = new ASRResponse();
            ASRUtil.aiParseAudio(callAudio.getFileLink(), response);
            callAudio.setIsAiParsed(response.getIsSuccess() == IsSuccessEnum.SUCCESS ? YesOrNoEnum.YES.getCode() : YesOrNoEnum.NO.getCode());
            callAudio.setIsSuccess(response.getIsSuccess().getCode());
            callAudio.setFailReason(response.getError());
            callAudio.setIsAiParsed(YesOrNoEnum.YES.getCode());
            callAudioMapper.updateById(callAudio);


            AudioText audioText = new AudioText();
            audioText.setContent(response.getResult());
            audioText.setCallId(callAudio.getId());
            audioTextMapper.insert(audioText);
//            audioTexts.add(audioText);
        }
//        callAudioMapper.updateById(callAudios);
//        audioTextMapper.insert(audioTexts);
        log.info("录音文件解析完毕。。。。");
        return RespVO.success(null);
    }
}

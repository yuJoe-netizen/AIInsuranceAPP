package com.example.api.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.api.aliyun.utils.ASRUtil;
import com.example.api.aliyun.vo.ASRResponse;
import com.example.common.RespVO;
import com.example.common.enums.CallRoleEnum;
import com.example.common.enums.IsSuccessEnum;
import com.example.common.enums.YesOrNoEnum;
import com.example.db.entity.AudioText;
import com.example.db.entity.AudioTextMerge;
import com.example.db.entity.CallAudio;
import com.example.db.mapper.AudioTextMapper;
import com.example.db.mapper.AudioTextMergeMapper;
import com.example.db.mapper.CallAudioMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/audio")
@AllArgsConstructor
@Slf4j
public class AudioController {

    private final CallAudioMapper callAudioMapper;
    private final AudioTextMapper audioTextMapper;
    private final AudioTextMergeMapper audioTextMergeMapper;


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


    @GetMapping("/audioTextMerge")
    public RespVO<Void> audioTextMerge(){
        log.info("开始执行合并");
        //查询audio_text表数据,使用mybatis-plus框架
        QueryWrapper<AudioText> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("call_id", "start_time");
        List<AudioText> audioTexts = audioTextMapper.selectList(queryWrapper);
        Map<Integer, List<AudioText>> map = audioTexts.stream().collect(Collectors.groupingBy(AudioText::getCallId));
        List<AudioTextMerge> audioTextMerges  = new ArrayList<>();
        List<CallAudio> callAudios = callAudioMapper.selectList(null);
        Map<Integer, List<CallAudio>> callAudioMap = callAudios.stream().collect(Collectors.groupingBy(CallAudio::getId));
        for (Map.Entry<Integer, List<AudioText>> entry : map.entrySet()) {
            List<AudioText> value = entry.getValue();
            AudioTextMerge audioTextMerge = BeanUtil.copyProperties(value.get(0), AudioTextMerge.class);
            CallAudio callAudio = callAudioMap.get(value.get(0).getCallId()).get(0);
            audioTextMerge.setCallee(callAudio.getCallee());
            audioTextMerge.setRoleName(CallRoleEnum.getByCode(audioTextMerge.getRoleId()).getDesc());
            audioTextMerges.add(audioTextMerge);
            AudioTextMerge pre = audioTextMerge;
            for (int i = 1; i < value.size(); i++) {
                AudioText cur = value.get(i);
                if (!Objects.equals(pre.getRoleId(),cur.getRoleId())){
                    AudioTextMerge audioTextMergeCur = BeanUtil.copyProperties(cur, AudioTextMerge.class);
                    CallAudio ca = callAudioMap.get(cur.getCallId()).get(0);
                    audioTextMergeCur.setCallee(ca.getCallee());
                    audioTextMergeCur.setRoleName(CallRoleEnum.getByCode(audioTextMergeCur.getRoleId()).getDesc());
                    audioTextMerges.add(audioTextMergeCur);
                    pre=audioTextMergeCur;
                }else {
                    pre.setContent(pre.getContent()+";"+cur.getContent());
                    pre.setEndTime(cur.getEndTime());
                }
            }
        }
        audioTextMergeMapper.insert(audioTextMerges);

        log.info("合并完毕");
        return RespVO.success(null);
    }

}

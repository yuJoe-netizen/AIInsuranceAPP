package com.example.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CallAudio implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String fileLink;
    private String isDeleted;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private String isAiParsed;
    private String caseNo;
    private String callee;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outboundTime;
    private Integer callDuration;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date hangUpTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date audioCreatedTime;
    private String callId;
    /**
     * 是否解析成功
     */
    private String isSuccess;
    /**
     * 解析失败原因
     */
    private String failReason;

}

package com.example.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Data
public class AudioTextMerge implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 通话id
     */
    private Integer callId ;
    /**
     * -- 通话文本
     */
	private String content ;

    /**
     * 被叫号码
     */
    private String callee;
    /**
     * 角色id
     */

    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 开始时间
     */
    private BigInteger startTime;
    /**
     * 结束时间
     */
    private BigInteger endTime;
    /**
     *  逻辑删除
     */
    private String isDeleted ;
    /**
     *  创建人
     */
    private String createdBy ;
    /**
     *  创建时间
     */
    private Date createdDate ;
    /**
     *   更新时间
     */
    private Date updatedDate ;
    /**
     *   更新时间
     */
    private String updatedBy;
}
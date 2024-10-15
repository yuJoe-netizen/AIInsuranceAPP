package com.example.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_case")
public class Case extends BaseEntity{

    @TableId(type = IdType.AUTO)
    private Integer id ; // 主键
    private String applyNo ; // 申请号
    private Integer overdueDay ; // 逾期天数
    private BigDecimal remainingPrincipal ; // 剩余本金
    private String caseStatus ; // 案件状态
    private String todayProgress ; // 今日进展
    private String followUpRequest ; // 跟进要求
    private String executionStrategy ; // 执行策略
    private String customer ; // 客户姓名
}

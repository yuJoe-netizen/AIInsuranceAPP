package com.example.db.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.db.entity.Case;
import com.example.db.mapper.CaseMapper;
import com.example.db.services.CaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CaseServiceImpl extends ServiceImpl<CaseMapper, Case> implements CaseService {
    private final CaseMapper caseMapper;
}

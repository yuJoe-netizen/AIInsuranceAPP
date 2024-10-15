package com.example.api.tcase;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.RespVO;
import com.example.db.dto.in.CaseListReqDTO;
import com.example.db.entity.Case;
import com.example.db.mapper.CaseMapper;
import com.example.db.services.CaseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 可见查询
 */
@RestController
@RequestMapping("/case")
@AllArgsConstructor
public class CaseController {


    private final CaseMapper caseMapper;
    private final CaseService caseService;




    @RequestMapping("/getCaseList")
    public RespVO<Page<Case>> getCaseList(@RequestBody CaseListReqDTO request) {

        Page<Case> page = caseMapper.selectPage(new Page<>(request.getPageNum(), request.getPageSize()), null);

        return RespVO.success(page);
    }
}

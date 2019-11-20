package com.rgk.workprocess.controller;

import com.rgk.workprocess.domain.ReturnObject;
import com.rgk.workprocess.service.activiti.IProEngService;
import com.rgk.workprocess.service.activiti.impl.ProHisServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    ProHisServiceImpl proHisService;

    @RequestMapping("/getByInstanceId")
    public ReturnObject startProcess(String processInstanceId) {
        return proHisService.getHistorDetail(processInstanceId);
    }
}

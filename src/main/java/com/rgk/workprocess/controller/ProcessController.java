package com.rgk.workprocess.controller;

import com.rgk.workprocess.domain.ReturnObject;
import com.rgk.workprocess.service.activiti.IProEngService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    IProEngService proEngService;

    @RequestMapping("/start")
    public ReturnObject startProcess(String orderId,
                                     @RequestParam(defaultValue = "",required = false) String message,
                                     @AuthenticationPrincipal String username) {

        log.info("username = {}", username);



        return proEngService.startProcess(orderId, message);
    }

    @RequestMapping("/queryTask")
    public ReturnObject queryTask(@RequestParam(value = "userId")String userId,
                                  @RequestParam(value = "page", defaultValue = "0") Integer page,
                                  @RequestParam(value = "rows", defaultValue = "20") Integer rows,
                                  @RequestParam(value = "sidx", required = false, defaultValue = "createdDate") String sidx,
                                  @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort) {

        return proEngService.queryTask(userId,page, rows, sidx, sort);
    }

    @RequestMapping("/assigneeTask")
    public ReturnObject assigneeTask(String taskId, String toUser, String message) {
        return proEngService.assigneeTask(taskId,toUser, message);
    }

    @RequestMapping("/wrokResult")
    public ReturnObject wrokResult(String taskId, String message, int result) {
        return proEngService.wrokResult(taskId,message, result);
    }

    @RequestMapping("/checkResult")
    public ReturnObject checkResult(String taskId, String message, int result) {
        return proEngService.checkResult(taskId,message, result);
    }


}

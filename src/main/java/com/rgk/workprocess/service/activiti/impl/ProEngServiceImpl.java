package com.rgk.workprocess.service.activiti.impl;

import com.google.common.collect.Maps;
import com.rgk.workprocess.constant.FormState;
import com.rgk.workprocess.domain.ReturnObject;
import com.rgk.workprocess.entity.MCustomOrder;
import com.rgk.workprocess.entity.MProcessType;
import com.rgk.workprocess.respository.ICustomerOrderDao;
import com.rgk.workprocess.respository.IProcessTypeDao;
import com.rgk.workprocess.service.activiti.IProEngService;
import com.rgk.workprocess.service.activiti.activtiForm.AssigneeForm;
import com.rgk.workprocess.service.activiti.activtiForm.CheckResultForm;
import com.rgk.workprocess.service.activiti.activtiForm.StartForm;
import com.rgk.workprocess.service.activiti.activtiForm.WorkResultForm;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class ProEngServiceImpl implements IProEngService {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    ICustomerOrderDao customerOrderDao;

    @Autowired
    FormService formService;

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    IProcessTypeDao processTypeDao;

    @Autowired
    TaskService taskService;

    @Override
    public ReturnObject startProcess(String orderId, String message) {
        ReturnObject ro = new ReturnObject(-1, "", "error");
        MCustomOrder customOrder = customerOrderDao.getOne(orderId);
        if (customOrder == null) {
            return ro;
        }

        if (customOrder.getState() == FormState.start) {
            return ro;
        }

        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();

        Map<String, Object> variables = Maps.newHashMap();
        variables.put(StartForm.orderId, customOrder.getId());
        variables.put(StartForm.customerServiceNotes, message);
        variables.put(StartForm.inititorId, "wo");

        MProcessType processType = processTypeDao.getOne(customOrder.getProcessType());
        List<MProcessType> byProKey = processTypeDao.findByProKey(processType.getProKey());
        if (CollectionUtils.isEmpty(byProKey)) {
            ro.setMessage("no deployment key{"+byProKey+"}");
            return ro;
        }
        
        ProcessInstance start = runtimeService.createProcessInstanceBuilder()
                .businessKey(uuidStr)
                .processDefinitionId(byProKey.get(0).getProId())
                .variables(variables)
                .start();
        if (start == null) {
            return ro;
        }

        log.info("start processInstance = {}", start);

        return ro.setResult(ToStringBuilder.reflectionToString(start, ToStringStyle.JSON_STYLE))
                .setMessage("OK").setCode(1);
    }

    @Override
    public ReturnObject queryTask(String userId,Integer page,Integer rows,String sidx ,String sort) {
        List<Task> userTask = taskService
                .createTaskQuery()
                .taskCandidateOrAssigned(userId)
                .listPage(page, rows);

        log.info("query Usertask = {}", userTask.toString());

        ReturnObject result = new ReturnObject();
        result.setResult(userTask.toString());

        return result;
    }

    @Override
    public ReturnObject assigneeTask(String taskId, String toUser, String message) {
        Map map = Maps.newHashMap();
        map.put(AssigneeForm.assigneeUser, toUser);
        map.put(AssigneeForm.assigneeMessage, message);

        //TODO 当前用户先认领这个TASK，认领后只有该用户可以看到这个TASK，现在取不到用户信息，先写死一个
        String user = "jingyi.liu";
        taskService.claim(taskId, user);

        taskService.setVariables(taskId,map);
        taskService.complete(taskId);

        return new ReturnObject();
    }

    @Override
    public ReturnObject wrokResult(String taskId, String message, int result) {
        Map map = Maps.newHashMap();
        map.put(WorkResultForm.workMessage, message);
        map.put(WorkResultForm.workResult, result);

        //TODO 当前用户先认领这个TASK，认领后只有该用户可以看到这个TASK，现在取不到用户信息，先写死一个
        String user = "jingyi.liu";
        taskService.claim(taskId, user);

        taskService.setVariables(taskId,map);
        taskService.complete(taskId);

        return new ReturnObject();
    }

    @Override
    public ReturnObject checkResult(String taskId, String message, int result) {
        Map map = Maps.newHashMap();
        map.put(CheckResultForm.checkMessage, message);
        map.put(CheckResultForm.checkResult, result);

        //TODO 当前用户先认领这个TASK，认领后只有该用户可以看到这个TASK，现在取不到用户信息，先写死一个
        String user = "jingyi.liu";
        taskService.claim(taskId, user);

        taskService.setVariables(taskId,map);
        taskService.complete(taskId);

        return new ReturnObject();
    }
}

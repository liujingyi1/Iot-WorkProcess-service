package com.rgk.workprocess.service.impl;

import com.alibaba.fastjson.JSON;
import com.rgk.workprocess.domain.ProcessDetailView;
import com.rgk.workprocess.domain.TaskView;
import com.rgk.workprocess.respository.ICustomerOrderDao;
import com.rgk.workprocess.service.IPageService;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.FormService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class PageServiceImpl implements IPageService {

    @Autowired
    ICustomerOrderDao customerOrderDao;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Override
    public ProcessDetailView getProcessDetail(String id, Model model) {
        ProcessDetailView processDetailView = customerOrderDao.findProcessDetailViewById(id);

        String taskId = processDetailView.getCustomOrder().getTaskId();

        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        List<String> properties = new ArrayList<String>();
        for (FormProperty formProperty : formProperties) {
            properties.add(JSON.toJSONString(formProperty));
            log.info("formProperty = {}", JSON.toJSONString(formProperty));
        }

        String taskDefinitionKey = taskService
                .createTaskQuery()
                .taskId(taskId)
                .singleResult()
                .getTaskDefinitionKey();

        TaskView taskView = new TaskView();
        taskView.setTaskId(taskId);
        taskView.setTaskKey(taskDefinitionKey);
        taskView.setFormProperty(properties);

        processDetailView.setTaskView(taskView);

        log.info("processDetailView = {}", processDetailView);

        model.addAttribute("order", processDetailView);
        return processDetailView;
    }
}

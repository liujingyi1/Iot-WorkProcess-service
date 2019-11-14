package com.rgk.workprocess.listener;

import com.rgk.workprocess.constant.FormState;
import com.rgk.workprocess.entity.MCustomOrder;
import com.rgk.workprocess.respository.ICustomerOrderDao;
import com.rgk.workprocess.service.activiti.activtiForm.AssigneeForm;
import com.rgk.workprocess.service.activiti.activtiForm.StartForm;
import com.rgk.workprocess.utils.ApplicationContextUtils;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;


@Log4j2
public class EngineerUserTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("EngineerUserTaskListener-------");

        String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
        String executionId = delegateTask.getExecutionId();
        String name = delegateTask.getName();
        log.info("taskDefinitionKey = {}, executionId = {}, name = {}",
                taskDefinitionKey,executionId,name);

        String userId = (String) delegateTask.getVariable(AssigneeForm.assigneeUser);
        delegateTask.setAssignee(userId);

        String orderId = (String) delegateTask.getVariable(StartForm.orderId);
        ICustomerOrderDao customerOrderService= ApplicationContextUtils.getBean(ICustomerOrderDao.class);
        MCustomOrder customOrder = (MCustomOrder) customerOrderService.getOne(orderId);
        customOrder.setState(FormState.process);
        customOrder.setHandlePerson(userId);
        customerOrderService.save(customOrder);
    }
}

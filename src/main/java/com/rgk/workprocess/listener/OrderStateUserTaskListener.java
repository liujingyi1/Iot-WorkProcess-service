package com.rgk.workprocess.listener;

import com.rgk.workprocess.constant.FormState;
import com.rgk.workprocess.entity.MCustomOrder;
import com.rgk.workprocess.entity.MProcessState;
import com.rgk.workprocess.respository.ICustomerOrderDao;
import com.rgk.workprocess.respository.IProcessStateDao;
import com.rgk.workprocess.service.activiti.activtiForm.AssigneeForm;
import com.rgk.workprocess.service.activiti.activtiForm.StartForm;
import com.rgk.workprocess.utils.ApplicationContextUtils;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;


@Log4j2
public class OrderStateUserTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
        log.info("OrderStateUserTaskListener-- taskDefinitionKey1 = {}", taskDefinitionKey);

        IProcessStateDao processStateDao= ApplicationContextUtils.getBean(IProcessStateDao.class);
        MProcessState processState = processStateDao.findByTaskId(taskDefinitionKey);

        String orderId = (String) delegateTask.getVariable(StartForm.orderId);
        ICustomerOrderDao customerOrderService= ApplicationContextUtils.getBean(ICustomerOrderDao.class);
        MCustomOrder customOrder = (MCustomOrder) customerOrderService.getOne(orderId);
        customOrder.setState(Integer.parseInt(processState.getState()));
        customOrder.setTaskId(delegateTask.getId());
        customerOrderService.save(customOrder);
    }
}

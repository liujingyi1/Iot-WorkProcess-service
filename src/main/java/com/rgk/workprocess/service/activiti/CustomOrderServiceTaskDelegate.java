package com.rgk.workprocess.service.activiti;

import com.rgk.workprocess.constant.FormState;
import com.rgk.workprocess.entity.MCustomOrder;
import com.rgk.workprocess.respository.ICustomerOrderDao;
import com.rgk.workprocess.service.activiti.activtiForm.StartForm;
import com.rgk.workprocess.service.impl.CustomerOrderServiceImpl;
import com.rgk.workprocess.utils.ApplicationContextUtils;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.util.Map;

@Log4j2
public class CustomOrderServiceTaskDelegate implements JavaDelegate {
    private static final Object TAG = "ServiceTaskDelegate";

    @Override
    public void execute(DelegateExecution execution) {
        log.info("ServiceTaskDelegate------------");

        ICustomerOrderDao customerOrderService= ApplicationContextUtils.getBean(ICustomerOrderDao.class);

        Map<String, Object> variables = execution.getVariables();
        for (String s : variables.keySet()) {
            log.info("key = {}, value = {}", s, variables.get(s));
        }

        String orderId = (String) variables.get(StartForm.orderId);

        MCustomOrder customOrder = (MCustomOrder) customerOrderService.getOne(orderId);
        customOrder.setState(FormState.start);
        customOrder.setProcessInstanceId(execution.getProcessInstanceId());
        customOrder.setBusinessKey(execution.getProcessInstanceBusinessKey());
        customerOrderService.save(customOrder);
    }
}

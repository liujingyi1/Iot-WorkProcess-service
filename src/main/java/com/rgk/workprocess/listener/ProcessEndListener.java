package com.rgk.workprocess.listener;

import lombok.extern.log4j.Log4j2;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

@Log4j2
public class ProcessEndListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("ProcessEndListener----------");
        log.info("delegateTask = {}", delegateTask);

    }

}

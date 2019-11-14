package com.rgk.workprocess.service;

import org.activiti.engine.repository.ProcessDefinition;

public interface IProcessTypeService {

    public void processDeploymentChanage(ProcessDefinition processDefinition);

}

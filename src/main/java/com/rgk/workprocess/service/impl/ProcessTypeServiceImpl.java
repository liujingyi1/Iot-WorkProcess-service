package com.rgk.workprocess.service.impl;

import com.rgk.workprocess.entity.MProcessType;
import com.rgk.workprocess.respository.IProcessTypeDao;
import com.rgk.workprocess.service.IProcessTypeService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ProcessTypeServiceImpl implements IProcessTypeService {

    @Autowired
    IProcessTypeDao processTypeDao;

    @Autowired
    RepositoryService repositoryService;

    @Override
    public void processDeploymentChanage(ProcessDefinition processDefinition) {
        if (processDefinition == null) {
            return;
        }
        String processDefinitionKey = processDefinition.getKey();

        List<ProcessDefinition> list = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .list();

        List<MProcessType> processTypeList = processTypeDao.findByProKey(processDefinitionKey);
        if (CollectionUtils.isEmpty(list)) {
            if (!CollectionUtils.isEmpty(processTypeList)) {
                processTypeDao.deleteByProKey(processDefinition.getKey());
            }
        } else {
            MProcessType processType;
            if (CollectionUtils.isEmpty(processTypeList)) {
                processType = new MProcessType();
            } else {
                processType = processTypeList.get(0);
            }
            processType.setProId(processDefinition.getId());
            processType.setProKey(processDefinition.getKey());
            processType.setProName(processDefinition.getName());
            processType.setVersion(processDefinition.getVersion());
            processTypeDao.save(processType);
        }
    }
}

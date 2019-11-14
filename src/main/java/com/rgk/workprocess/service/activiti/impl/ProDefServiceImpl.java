package com.rgk.workprocess.service.activiti.impl;

import com.rgk.workprocess.domain.*;
import com.rgk.workprocess.service.IProcessTypeService;
import com.rgk.workprocess.service.activiti.IProDefService;
import com.rgk.workprocess.utils.FilenameUtils;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

@Log4j2
@Service
public class ProDefServiceImpl implements IProDefService {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    IProcessTypeService processTypeService;

    @Override
    public ReturnObject deployProcDef(String tenantId, String category,
                                      String fileName, MultipartFile file) {
        log.info("deploy(category={}, file={}, tenantId={}, name={})", category, file.getName(), tenantId, fileName);

        InputStream fileInputStream = null;
        ReturnObject result = new ReturnObject();
        try {
            fileInputStream = file.getInputStream();
        } catch (IOException e) {
            result.setCode(ResponseCode.ERROR).setMessage(e.getMessage());
        }
        Deployment deployment = null;
        String basename = FilenameUtils.getBaseName(fileName);
        String extension = FilenameUtils.getExtension(fileName);
        if (extension.equals("zip") || extension.equals("bar")) {
            ZipInputStream zip = new ZipInputStream(fileInputStream);
            deployment = this.repositoryService.createDeployment().name(basename).category(category).tenantId(tenantId)
                    .addZipInputStream(zip).deploy();
        } else if (extension.equals("png")) {
            deployment = this.repositoryService.createDeployment().category(category).tenantId(tenantId).name(file.getName())
                    .addInputStream(fileName, fileInputStream).deploy();
        } else if (fileName.indexOf("bpmn20.xml") != -1) {
            deployment = this.repositoryService.createDeployment().name(basename).category(category).tenantId(tenantId)
                    .addInputStream(fileName, fileInputStream).deploy();
        } else if (extension.equals("bpmn")) { // bpmn扩展名特殊处理，转换为bpmn20.xml
            String baseName = FilenameUtils.getBaseName(fileName);
            deployment = this.repositoryService.createDeployment().name(basename).category(category).tenantId(tenantId)
                    .addInputStream(baseName + ".bpmn20.xml", fileInputStream).key("jingyi000").deploy();
        } else {
            return result.setCode(ResponseCode.ERROR_UNSUPPORTED_MEDIA_TYPE).setMessage("不支持的格式");
        }

        if (deployment == null) {
            return result.setCode(ResponseCode.ERROR).setMessage("流程部署失败");
        }

        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).list();

        if (CollectionUtils.isEmpty(list)) {
            result.setCode(ResponseCode.ERROR).setMessage("流程部署失败");
        }else {
            result.setResult(ToStringBuilder.reflectionToString(deployment, ToStringStyle.JSON_STYLE));
            onDeploymentChange(list.get(0));
        }

        return result;
    }

    @Override
    public ReturnObject listDeployments() {
        List<Deployment> list = repositoryService
                .createDeploymentQuery().list();

        return getListReturn(list);
    }

    @Override
    public ReturnObject findDeploymentById(String id) {
        Deployment deployment = repositoryService.createDeploymentQuery()
                .deploymentId(id).singleResult();

        DeploymentBean deploymentBean = new DeploymentBean();
        BeanUtils.copyProperties(deployment, deploymentBean);
        int size = runtimeService.createProcessInstanceQuery()
                .deploymentId(deployment.getId()).list().size();
        deploymentBean.setRunProcessCount(size);
        deploymentBean.setSuspended(getDeploymentIsSuspended(deployment));

        ReturnObject returnObject = new ReturnObject();
        returnObject.setResult(deploymentBean);

        return returnObject;
    }

    @Override
    public ReturnObject findDeploymentByTenantId(String tenantId) {
        List<Deployment> list = repositoryService
                .createDeploymentQuery()
                .deploymentTenantId(tenantId)
                .list();

        return getListReturn(list);
    }

    @Override
    public ReturnObject findDeploymentByNameAndTenantId(String name, String tenantId) {
        List<Deployment> list = repositoryService
                .createDeploymentQuery()
                .deploymentName(name)
                .deploymentTenantId(tenantId)
                .list();
        return getListReturn(list);
    }

    @Override
    public ReturnObject suspendProcess(String id) {
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery().deploymentId(id).singleResult();

        ReturnObject returnObject = new ReturnObject();
        if (processDefinition == null) {
            return returnObject.setCode(ResponseCode.ERROR_NO_CONTENT)
                    .setResult("not found the process");
        }
        repositoryService.suspendProcessDefinitionById(processDefinition.getId());
        return returnObject;
    }

    @Override
    public ReturnObject activateProcess(String id) {
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery().deploymentId(id).singleResult();

        ReturnObject returnObject = new ReturnObject();
        if (processDefinition == null) {
            return returnObject.setCode(ResponseCode.ERROR_NO_CONTENT)
                    .setResult("not found the process");
        }
        repositoryService.activateProcessDefinitionById(processDefinition.getId());
        return returnObject;
    }

    @Override
    public ReturnObject deleteDeployment(String id) {
        try {
            ProcessDefinition processDefinition = repositoryService
                    .createProcessDefinitionQuery()
                    .deploymentId(id)
                    .singleResult();
            repositoryService.deleteDeployment(id);
            onDeploymentChange(processDefinition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReturnObject();
    }

    private ReturnObject getListReturn(List<Deployment> list) {
        int start = 0;

        DeploymentListBean deploymentListBean = new DeploymentListBean();
        List<DeploymentBean> deploymentList = new ArrayList<DeploymentBean>();
        for (Deployment deployment : list) {
            DeploymentBean deploymentBean = new DeploymentBean();
            BeanUtils.copyProperties(deployment, deploymentBean);

            int size = runtimeService.createProcessInstanceQuery()
                    .deploymentId(deployment.getId()).list().size();
            deploymentBean.setRunProcessCount(size);
            start += size > 0 ? 1 : 0;

            deploymentBean.setSuspended(getDeploymentIsSuspended(deployment));

            deploymentList.add(deploymentBean);
            log.info("deployment = {}", deploymentBean);
        }

        if (deploymentList.size() > 0) {
            deploymentListBean.setData(deploymentList);
            deploymentListBean.setStart(start);
            deploymentListBean.setTotal(list.size());
        }

        ReturnObject returnObject = new ReturnObject();
        returnObject.setResult(deploymentListBean);
        return returnObject;
    }

    boolean getDeploymentIsSuspended(Deployment deployment) {
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        if (processDefinition == null) {
            return false;
        }
        return repositoryService.isProcessDefinitionSuspended(processDefinition.getId());
    }

    private void onDeploymentChange(ProcessDefinition processDefinition) {
        processTypeService.processDeploymentChanage(processDefinition);
    }

    @Override
    public JGridPage<DeploymentBean> listDeployPage(Integer page, Integer rows, String sidx, String sort) {
        JGridPage<DeploymentBean> jGridPage = new JGridPage<DeploymentBean>();

        //暂时默认按部署时间升序排序
        List<Deployment> list = repositoryService
                .createDeploymentQuery()
                .orderByDeploymenTime()
                .asc()
                .listPage((page-1)*rows, (page-1)*rows+rows);

        List<DeploymentBean> deploymentList = new ArrayList<DeploymentBean>();
        for (Deployment deployment : list) {
            DeploymentBean deploymentBean = new DeploymentBean();
            BeanUtils.copyProperties(deployment, deploymentBean);

            int processInstanceSize = runtimeService.createProcessInstanceQuery()
                    .deploymentId(deployment.getId()).list().size();
            deploymentBean.setRunProcessCount(processInstanceSize);

            ProcessDefinition processDefinition = repositoryService
                    .createProcessDefinitionQuery()
                    .deploymentId(deployment.getId())
                    .singleResult();
            if (processDefinition != null) {
                deploymentBean.setName(processDefinition.getName());
            }

            deploymentBean.setSuspended(getDeploymentIsSuspended(deployment));

            deploymentList.add(deploymentBean);
            log.info("deployment = {}", deploymentBean);
        }

        jGridPage.setPage(page);
        jGridPage.setTotal(deploymentList.size());
        jGridPage.setRecords(list.size());
        jGridPage.setRows(deploymentList);

        return jGridPage;
    }
}

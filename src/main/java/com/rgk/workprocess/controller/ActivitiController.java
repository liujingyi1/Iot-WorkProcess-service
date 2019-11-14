package com.rgk.workprocess.controller;

import com.alibaba.fastjson.JSON;
import com.rgk.workprocess.domain.ReturnObject;
import com.rgk.workprocess.service.TestService;
import com.rgk.workprocess.service.activiti.impl.ProDefServiceImpl;
import com.rgk.workprocess.utils.ActivitiUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/processTest")
public class ActivitiController {

    @Autowired
    HistoryService historyService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

    private ProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();

    @Autowired
    ProDefServiceImpl proDefService;

    private static final Logger logger = LoggerFactory.getLogger(ActivitiController.class);

    @RequestMapping("/startProcess")
    public ReturnObject startProcess(String processKey) {
        List<ProcessDefinition> processDefinitions = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(processKey)
                .listPage(0, 100);
        logger.info("processDefinitions ${} count = {}", processKey, processDefinitions.size());

        int version = 0;
        ProcessDefinition deployProcessDefine = null;
        for (ProcessDefinition processDefinition : processDefinitions) {
            logger.info("deploy = {}, version = {}, category = {}, key = {}",
                    processDefinition,
                    processDefinition.getVersion(),
                    processDefinition.getCategory(),
                    processDefinition.getKey());

            if (processDefinition.getVersion() > version) {
                deployProcessDefine = processDefinition;
            }
        }

        ReturnObject returnObject = new ReturnObject();
        if (deployProcessDefine != null) {
            UUID uuid = UUID.randomUUID();
            String uuidStr = uuid.toString();

            ProcessInstance myProcess = runtimeService
                    .createProcessInstanceBuilder()
                    .businessKey(uuidStr)
                    .processDefinitionId(deployProcessDefine.getId())
                    .tenantId("tenantId2")
                    .start();
            returnObject.setResult(uuidStr);
            logger.info("deployProcessDefine version = {}", deployProcessDefine.getVersion());
            logger.info("uuid = {}", uuidStr);
        } else {
            logger.info("没有这个流程 #{}", processKey);
        }

//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess");

        return returnObject;
    }

    @RequestMapping("/queryProcessInstance/byBusiness")
    public ReturnObject queryProcessInstanceByBusiness(String business) {
        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery()
                .processInstanceBusinessKey(business)
                .singleResult();

        ReturnObject object = new ReturnObject();
        if (processInstance != null) {
            object.setResult(processInstance.getId());
            return object;
        }
        logger.info("query processInstance result = {}", JSON.toJSONString(object));
        return object;
    }

    @RequestMapping("/queryProcessInstance/byTask")
    public ReturnObject queryProcessInstanceByTask(String taskId) {
        Task task = taskService
                .createTaskQuery()
                .taskId(taskId)
                .singleResult();

        ReturnObject object = new ReturnObject();
        if (task != null) {
            String processInstanceId = task.getProcessInstanceId();
            object.setResult(processInstanceId);
            return object;
        }
        logger.info("query processInstance result = {}", JSON.toJSONString(object));

        return object;
    }

    @RequestMapping("/completeTask")
    public void completeTask(String taskId, String message) {
        Task task = taskService
                .createTaskQuery()
                .taskId(taskId)
                .singleResult();
        if (task != null) {
            taskService.setVariable(taskId, "msg", message);
            taskService.complete(taskId);
            logger.info("complete task: task = {}, message = {}", taskId, message);
        } else {
            logger.info("taskId错误");
        }
    }

    @RequestMapping("/assigneeTask")
    public ReturnObject assigneeTask(String taskId, String user) {

        Task task = taskService
                .createTaskQuery()
                .taskId(taskId)
                .singleResult();

        ReturnObject returnObject = new ReturnObject();
        if (task != null) {
            taskService.setAssignee(taskId, user);
            returnObject.setResult("Assignee Task to " + user + " success");
            logger.info( "Assignee Task to {} success" , user);
        } else {
            returnObject.setMessage("taskId错误");
            logger.info("taskId错误");
        }

        return returnObject;
    }

    @RequestMapping("/queryVariables")
    public ReturnObject queryVariables(String taskId) {
        Task task = taskService
                .createTaskQuery()
                .taskId(taskId)
                .singleResult();

        String name = task.getName();
        logger.info("name = {}", name);

        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();

        Map<String, VariableInstance> variableInstances = runtimeService.getVariableInstances(processInstance.getId());
        for (String s : variableInstances.keySet()) {
            logger.info("variable = {}", variableInstances.get(s));
        }

        ReturnObject returnObject = new ReturnObject();
        returnObject.setResult(variableInstances.toString());
        return returnObject;
    }

    @RequestMapping("/queryTaskAssinee")
    public ReturnObject queryTaskAssine(String userId) {
        List<Task> tasks = taskService
                .createTaskQuery()
                .taskAssignee(userId)
                .listPage(0, 100);

        List<String> list = new ArrayList<String>();
        for (Task task : tasks) {
            list.add(task.getId());
        }

        ReturnObject result = new ReturnObject();
        result.setResult(JSON.toJSONString(list));

        logger.info("query Assinee task result = {}", JSON.toJSONString(list));

        return result;
    }

    @RequestMapping("/queryTask")
    public ReturnObject queryTask(String userId) {
        List<Task> userTask = taskService
                .createTaskQuery()
                .taskCandidateUser(userId)
                .listPage(0, 100);


        List<String> list = new ArrayList<String>();
        for (Task task : userTask) {
            list.add(task.getId());
        }

        ReturnObject result = new ReturnObject();
        result.setResult(JSON.toJSONString(list));

        logger.info("query result = {}", JSON.toJSONString(list));

        return result;
    }

    @RequestMapping("/showImage")
    public void showImage(String instanceId, HttpServletResponse response) {

        /*
         * 参数校验
         */
        logger.info("查看完整流程图！流程实例ID:{}", instanceId);
        if (StringUtils.isBlank(instanceId)) return;


        /*
         *  获取流程实例
         */
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if (processInstance == null) {
            logger.error("流程实例ID:{}没查询到流程实例！", instanceId);
            return;
        }

        // 根据流程对象获取流程对象模型
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());

        /*
         *  查看已执行的节点集合
         *  获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
         */
        // 构造历史流程查询
        HistoricActivityInstanceQuery historyInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(instanceId);
        // 查询历史节点
        List<HistoricActivityInstance> historicActivityInstanceList = historyInstanceQuery.orderByHistoricActivityInstanceStartTime().asc().list();
        if (historicActivityInstanceList == null || historicActivityInstanceList.size() == 0) {
            logger.info("流程实例ID:{}没有历史节点信息！", instanceId);
            outputImg(response, bpmnModel, null, null);
            return;
        }
        // 已执行的节点ID集合(将historicActivityInstanceList中元素的activityId字段取出封装到executedActivityIdList)
        List<String> executedActivityIdList = historicActivityInstanceList.stream().map(item -> item.getActivityId()).collect(Collectors.toList());

        /*
         *  获取流程走过的线
         */
        // 获取流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
        List<String> flowIds = ActivitiUtils.getHighLightedFlows(bpmnModel, processDefinition, historicActivityInstanceList);


        /*
         * 输出图像，并设置高亮
         */
        outputImg(response, bpmnModel, flowIds, executedActivityIdList);


    }

    /**
     * <p>输出图像</p>
     *
     * @param response               响应实体
     * @param bpmnModel              图像对象
     * @param flowIds                已执行的线集合
     * @param executedActivityIdList void 已执行的节点ID集合
     * @author FRH
     * @time 2018年12月10日上午11:23:01
     * @version 1.0
     */
    private void outputImg(HttpServletResponse response, BpmnModel bpmnModel, List<String> flowIds, List<String> executedActivityIdList) {
        InputStream imageStream = null;
        try {
            if (flowIds == null && executedActivityIdList == null) {
                imageStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", "宋体", "微软雅黑", "黑体", null, 1.0d);
            } else if (flowIds == null) {
                imageStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", executedActivityIdList, 1.0d);
            } else if (executedActivityIdList == null) {
                imageStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", flowIds, 1.0d);
            } else {
                imageStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", executedActivityIdList, flowIds, "宋体", "微软雅黑", "黑体", null, 1.0d);
            }
            // 输出资源内容到相应对象
            byte[] b = new byte[1024];
            int len;
            while ((len = imageStream.read(b, 0, 1024)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
            response.getOutputStream().flush();
        } catch (Exception e) {
            logger.error("流程图输出异常！", e);
        } finally { // 流关闭
            try {
                imageStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            StreamUtils.closeInputStream(imageStream);
        }
    }
}

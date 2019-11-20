package com.rgk.workprocess.service.activiti.impl;

import com.rgk.workprocess.domain.HistoryDetailNode;
import com.rgk.workprocess.domain.ReturnObject;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class ProHisServiceImpl {
    @Autowired
    HistoryService historyService;

    public ReturnObject getHistorDetail(String processInstanceId) {
        List<HistoryDetailNode> historyDetailNodeList = new ArrayList<HistoryDetailNode>();

        List<HistoricDetail> list = historyService.createHistoricDetailQuery().processInstanceId(processInstanceId).orderByTime().asc().list();
        HistoryDetailNode tempDetailNode = null;
        String taskKey = "";
        for (HistoricDetail historicDetail : list) {
            HistoricVariableUpdate variable = (HistoricVariableUpdate) historicDetail;
            System.out.println("-----------------");

            String activityInstanceId = historicDetail.getActivityInstanceId();
            if (activityInstanceId != null) {
                if (tempDetailNode == null || tempDetailNode.getActivityInstanceId().compareTo(activityInstanceId) != 0) {
                    if (tempDetailNode != null) {
                        historyDetailNodeList.add(tempDetailNode);
                    }
                    tempDetailNode = new HistoryDetailNode();
                    tempDetailNode.setActivityInstanceId(activityInstanceId);

                    HistoricActivityInstance historicActivityInstance = historyService.createHistoricActivityInstanceQuery().activityInstanceId(activityInstanceId).singleResult();
                    String taskId = historicActivityInstance.getTaskId();

                    HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
                    String assignee = historicTaskInstance.getAssignee();
                    String taskName = historicTaskInstance.getName();
                    taskKey = historicTaskInstance.getTaskDefinitionKey();

                    tempDetailNode.setTaskId(taskId);
                    tempDetailNode.setTaskName(taskName);
                    tempDetailNode.setIdentity(assignee);
                    tempDetailNode.setDate(historicDetail.getTime());
//                    tempDetailNode.setDetailList(new ArrayList<HistoricDetail>());
                }

                System.out.println("taskKey: " + taskKey);
                if (taskKey.compareTo("assigneeUser") == 0) {
                    if (variable.getVariableName().compareTo("assigneeMessage")==0) {
                        tempDetailNode.setMessage(variable.getValue().toString());
                    } if (variable.getVariableName().compareTo("assigneeUser")==0) {
                        tempDetailNode.setResult("分配给："+variable.getValue().toString());
                    }
                } if (taskKey.compareTo("reportResult") == 0) {
                    if (variable.getVariableName().compareTo("workMessage")==0) {
                        tempDetailNode.setMessage(variable.getValue().toString());
                    } if (variable.getVariableName().compareTo("workResult")==0) {
                        String value = variable.getValue().toString();
                        if (value.compareTo("1") == 0) {
                            tempDetailNode.setResult("完成");
                        } else if (value.compareTo("2") == 0) {
                            tempDetailNode.setResult("退回");
                        }
                    }
                }if (taskKey.compareTo("checkResult") == 0) {
                    if (variable.getVariableName().compareTo("checkMessage")==0) {
                        tempDetailNode.setMessage(variable.getValue().toString());
                    } if (variable.getVariableName().compareTo("checkResult")==0) {
                        String value = variable.getValue().toString();
                        if (value.compareTo("0") == 0) {
                            tempDetailNode.setResult("审核通过");
                        } else if (value.compareTo("1") == 0) {
                            tempDetailNode.setResult("重新分配");
                        } else if (value.compareTo("2") == 0) {
                            tempDetailNode.setResult("重新维修");
                        }
                    }
                }
            }
        }
        if (tempDetailNode != null) {
            historyDetailNodeList.add(tempDetailNode);
        }

        System.out.println("historicDetailList: " + historyDetailNodeList.toString());
        return new ReturnObject().setResult(historyDetailNodeList);
    }
}

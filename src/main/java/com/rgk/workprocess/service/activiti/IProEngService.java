package com.rgk.workprocess.service.activiti;

import com.rgk.workprocess.domain.ReturnObject;

public interface IProEngService {

    ReturnObject startProcess(String orderId, String message);

    ReturnObject queryTask(String userId,Integer page,Integer rows,String sidx ,String sort);

    ReturnObject assigneeTask(String taskId, String toUser, String message);

    ReturnObject wrokResult(String taskId, String message, int result);

    ReturnObject checkResult(String taskId, String message, int result);
}

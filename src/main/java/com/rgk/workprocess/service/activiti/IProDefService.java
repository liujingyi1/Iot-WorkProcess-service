package com.rgk.workprocess.service.activiti;

import com.rgk.workprocess.domain.DeploymentBean;
import com.rgk.workprocess.domain.JGridPage;
import com.rgk.workprocess.domain.ReturnObject;
import org.springframework.web.multipart.MultipartFile;

public interface IProDefService {

    ReturnObject deployProcDef(String tenantId, String category, String fileName, MultipartFile file);

    ReturnObject listDeployments();

    ReturnObject findDeploymentById(String id);

    ReturnObject findDeploymentByTenantId(String tenantId);

    ReturnObject findDeploymentByNameAndTenantId(String name, String tenantId);

    ReturnObject suspendProcess(String id);

    ReturnObject activateProcess(String id);

    ReturnObject deleteDeployment(String id);

    JGridPage<DeploymentBean> listDeployPage(Integer page, Integer rows, String sidx, String sord);
}

package com.rgk.workprocess.controller;

import com.rgk.workprocess.domain.DeploymentBean;
import com.rgk.workprocess.domain.JGridPage;
import com.rgk.workprocess.domain.ReturnObject;
import com.rgk.workprocess.service.activiti.impl.ProDefServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/repository")
public class RepositoryController {

    @Autowired
    ProDefServiceImpl proDefService;

    @RequestMapping(value = "/deploy", method = RequestMethod.POST)
    public ReturnObject deployProcess(
            @RequestParam("tenantId") String tenantId,
            @RequestParam("category") String category,
            @RequestParam("fileName") String fileName,
            @RequestPart("file") MultipartFile file) {
        return proDefService.deployProcDef(tenantId, category, fileName, file);
    }

    @RequestMapping("/deployments")
    public ReturnObject listDeployments() {
        return proDefService.listDeployments();
    }

    @RequestMapping("/listDeployPage")
    public JGridPage<DeploymentBean> listDeployPage(
                @RequestParam(value = "page", defaultValue = "0") Integer page,
                @RequestParam(value = "rows", defaultValue = "20") Integer rows,
                @RequestParam(value = "sidx", required = false, defaultValue = "createdDate") String sidx,
                @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort) {
        return proDefService.listDeployPage(page,rows,sidx,sort);
    }

    @RequestMapping("/findById")
    public ReturnObject findDeploymentById(String id) {
        return proDefService.findDeploymentById(id);
    }

    @RequestMapping("/findByTenantId")
    public ReturnObject findDeploymentByTenantId(String tenantId) {
        return proDefService.findDeploymentByTenantId(tenantId);
    }

    @RequestMapping("/findByNameAndTenantId")
    public ReturnObject findDeploymentByTenantId(String name, String tenantId) {
        return proDefService.findDeploymentByNameAndTenantId(name, tenantId);
    }

    @RequestMapping("/suspendProcess")
    public ReturnObject suspendProcess(String id) {
        return proDefService.suspendProcess(id);
    }

    @RequestMapping("/activateProcess")
    public ReturnObject activateProcess(String id) {
        return proDefService.activateProcess(id);
    }

    @RequestMapping("/delete")
    public ReturnObject deleteDeployment(String id) {
        return proDefService.deleteDeployment(id);
    }
}

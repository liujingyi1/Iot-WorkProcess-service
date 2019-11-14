package com.rgk.workprocess.controller;

import com.rgk.workprocess.domain.ReturnObject;
import com.rgk.workprocess.remote.UserService;
import com.rgk.workprocess.service.activiti.UserTaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    UserTaskService userTaskService;

    @RequestMapping(value = "/engineers")
    public ReturnObject getEngineers() {

        List<String> engineerUserList = userTaskService.getEngineerUserList();

        for (String s : engineerUserList) {
            log.info("s = {}", s);
        }
        log.info("engreList = {}", engineerUserList);

        ReturnObject object = new ReturnObject();
        object.setResult(engineerUserList);
        return object;
    }

    @Autowired
    UserService userService;

    @RequestMapping("/role")
    public Object getUsers() {
        Object object = userService.findUsers("402844826e20beae016e2128e3880001");
        log.info(object);
        return object;
    }


    @RequestMapping("/role/all")
    public Object getAllRole() {
        Object object = userService.findRoleAll(villageId());
        return object;
    }
}

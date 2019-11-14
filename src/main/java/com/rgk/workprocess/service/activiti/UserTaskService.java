package com.rgk.workprocess.service.activiti;

import com.netflix.discovery.converters.Auto;
import com.rgk.workprocess.domain.ReturnObject;
import com.rgk.workprocess.remote.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserTaskService {

    private static final String TAG = "UserTaskService";

    @Autowired
    UserService userService;

    public List<String> getUserTaskUserList1() {
        System.out.println(TAG +" user--------------");

        List<String> userList = new ArrayList<String>();
        userList.add("xiaohong");
        userList.add("xiaoming");
        userList.add("madongmei");

        return userList;
    }

    public List<String> getUserTaskGroupList1() {
        System.out.println(TAG +" group--------------");

        List<String> groupList = new ArrayList<String>();
        groupList.add("zhuguan");
        groupList.add("weixiuyuan");

        return groupList;
    }

    public List<String> getUserTaskUserList2() {
        System.out.println(TAG +" user--------------");

        List<String> userList = new ArrayList<String>();
        userList.add("xiaohong2");
        userList.add("xiaoming2");
        userList.add("madongmei2");

        return userList;
    }

    public List<String> getUserTaskGroupList2() {
        System.out.println(TAG +" group--------------");

        List<String> groupList = new ArrayList<String>();
        groupList.add("zhuguan2");
        groupList.add("weixiuyuan2");

        return groupList;
    }

    public List<String> getExecutiveUserList() {
        System.out.println(TAG +" group--------------");

        List<String> userNameList = new ArrayList<String>();

        ReturnObject returnObject = userService.findUsers("402844826e20beae016e2128e3880001");
        List<Map<String, Object>> userList = (List<Map<String, Object>>)returnObject.getResult();
        for (Map<String, Object> stringObjectMap : userList) {
            userNameList.add((String)stringObjectMap.get("username"));
        }


        return userNameList;
    }

    public List<String> getExecutiveGroupList() {
        System.out.println(TAG +" group--------------");

        List<String> groupList = new ArrayList<String>();
        groupList.add("ExecutiveGroup1");
        groupList.add("ExecutiveGroup2");

        return groupList;
    }

    public List<String> getEngineerUserList() {
        System.out.println(TAG +" group--------------");

        List<String> userNameList = new ArrayList<String>();

        ReturnObject returnObject = userService.findUsers("402844826e20beae016e2128e3880001");
        List<Map<String, Object>> userList = (List<Map<String, Object>>)returnObject.getResult();
        for (Map<String, Object> stringObjectMap : userList) {
            userNameList.add((String)stringObjectMap.get("username"));
        }


        return userNameList;
    }

    public List<String> getEngineerGroupList() {
        System.out.println(TAG +" group--------------");

        List<String> groupList = new ArrayList<String>();
        groupList.add("EngineerGroup1");
        groupList.add("EngineerGroup2");

        return groupList;
    }

}

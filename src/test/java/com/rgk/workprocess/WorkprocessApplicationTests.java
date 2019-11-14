package com.rgk.workprocess;

import com.alibaba.fastjson.JSON;
import com.rgk.workprocess.domain.HistoryDetailNode;
import com.rgk.workprocess.domain.ProcessDetailView;
import com.rgk.workprocess.domain.ReturnObject;
import com.rgk.workprocess.entity.MCustomOrder;
import com.rgk.workprocess.entity.MProcessState;
import com.rgk.workprocess.remote.UserService;
import com.rgk.workprocess.respository.ICustomerOrderDao;
import com.rgk.workprocess.respository.IProcessStateDao;
import com.rgk.workprocess.service.TestService;
import com.rgk.workprocess.utils.FilenameUtils;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.*;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkprocessApplicationTests {


    @Autowired
    RuntimeService runtimeService;

    @Autowired
    RepositoryService repositoryService;

    @Test
    public void contextLsdfoads() {
        MCustomOrder customOrder = new MCustomOrder();
        customOrder.setCustomerName("jingyi");
        customOrder.setCustomerPhone("1231231231");
        customOrder.setOrderTitle("shui guan huai le");
        customOrder.setOrderSummary("kuai dian xiu");
        customOrder.setProcessType("WorkTaskProcess");

        log.info(JSON.toJSONString(customOrder));

        MCustomOrder customOrder2 = new MCustomOrder();
        customOrder2.setCustomerName("jingyi");

        BeanUtils.copyProperties(customOrder2,customOrder);

        log.info(JSON.toJSONString(customOrder));

    }



    @Test
    public void deleteDeployment() {

        List<Execution> usertask2 = runtimeService.createExecutionQuery(). activityId("usertask2").listPage(0, 100);
        for (Execution execution : usertask2) {
            log.info("execution = {}", execution);

            Map<String, VariableInstance> variableInstances = runtimeService.getVariableInstances(execution.getId());
            for (String s : variableInstances.keySet()) {
                log.info("msg = {}", s);
            }
        }
        List<Deployment> deployments = repositoryService.createDeploymentQuery().listPage(0, 100);
        for (Deployment deployment : deployments) {
            log.info("deploymement = {}", deployment);
            repositoryService.deleteDeployment(deployment.getId());

        }
    }

    @Test
    public void deploy() {
        String filename = "I:/work/Activiti-demo2/src/main/resources/processes/WorkTaskProcess1.bpmn";

        File file = new File(filename);
        boolean exists = file.exists();
        log.info(String.valueOf(exists));

        Deployment deploy = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            String basename = FilenameUtils.getBaseName(filename);
            deploy = repositoryService.createDeployment()
                    .name(basename)
                    .addInputStream(basename + ".bpmn20.xml", fileInputStream)
//                    .tenantId("tenantId2")
//                    .category("category2")
//                    .key("key2")
                    .deploy();
        }catch (Exception e) {
            e.printStackTrace();
        }

        log.info("deploy = {}", deploy);
    }


    @Test
    public void assignUser() {

        

    }

    @Autowired
    IProcessStateDao processStateDao;
    @Test
    public void testTaskState() {
        List<MProcessState> all = processStateDao.findAll();
        for (MProcessState mProcessState : all) {
            log.info(mProcessState.toString());
        }

        MProcessState state = processStateDao.findByTaskId("assigneeUser");
        log.info("state = {}", state.toString());

    }

    @Test
    public void TestAAA() {
        int[] s = {2,7,11,15};
        int[] ints = towSum(s, 9);
        for (int anInt : ints) {
            log.info(anInt);
        }

        String test = "pwwkew";
        String temp = test.substring(0, test.length());
        
        for (char a : temp.toCharArray()) {


        }
    }

    @Test
    public void TestB() {
        int len = lengthOfLongestSubstring("abc");
        log.info(len);

//        int[] nums1 = {1,2,3,4};
//        int[] nums2 = {2,3,4,5};
//        int[] nums1 = {1,3};
//        int[] nums2 = {2};
//        double medianSortedArrays = findMedianSortedArrays(nums1, nums2);
//        log.info(medianSortedArrays);

    }


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        int nums1Len = nums1.length;
        int nums2Len = nums2.length;

        int[] temp1 = nums1Len > nums2Len ? nums2 : nums1;
        int[] temp2 = nums1Len <= nums2Len ? nums2 : nums1;
        int m = temp1.length;
        int n = temp2.length;

        //i: 0 ~ m
        //j((m+n+1)/2-i): (m+n+1)/2-m ~ (m+n+1)/2-0
        //except: temp1[i-1] <= temp2[j] & temp2[j-1] <= temp1[i]
        int i = (m+1) / 2;
        int j = (m + n + 1) / 2 - i;
        int iStart = 0, iEnd = m;
        while(true) {
            if ((i>0 && j<n) && (temp1[i-1] > temp2[j])) {
                iEnd = i;
                i = (iStart+i)/2;
                j = (m+n+1)/2-i;
                continue;
            }
            if((i<m && j>0) && (temp2[j-1] > temp1[i])){
                iStart = i;
                i = (iEnd+i+1)/2;
                j = (m+n+1)/2-i;
                continue;
            }
            break;
        }

        double result = 0;
        if ((m+n)%2 == 0) {
            int left = Math.max(i == 0 ? Integer.MIN_VALUE : temp1[i-1],j == 0 ? Integer.MIN_VALUE : temp2[j-1]);
            int right = Math.min(i == m ? Integer.MAX_VALUE : temp1[i],j == n ? Integer.MAX_VALUE : temp2[j]);
            log.info("left = {}, right = {}",left, right);
            result = (double)(left+right) / (double) 2;
        } else {
            result = (double)(Math.max(temp1[i-1],temp2[j-1]));
        }
        return result;
    }


    public int lengthOfLongestSubstring(String s) {

        for (int i = s.length(); i > 0; i--) {
            for (int j = 0; j+i < s.length()+1; j++) {

                char[] temp = s.substring(j, j+i).toCharArray();
                boolean equal = false;
                for (int t = 0; t < temp.length; t++) {
                    for (int m = t+1; m < temp.length; m++) {
                        if (temp[t] == temp[m]) {
                            equal = true;
                            break;
                        }
                    }
                    if (equal) {
                        break;
                    }
                }
                if (!equal) {
                    log.info("--------");
                    System.out.println(new String(temp));
                    return i;
                }
            }
        }
        return s.length();
    }

    public int lengthOfLongestSubstring1(String s) {
        char[] temp = s.toCharArray();
        boolean equal = false;
        for (int i = 0; i < temp.length; i++) {
            for (int j = i+1; j < temp.length; j++) {
                if (temp[i] == temp[j]) {
                    equal = true;
                    break;
                }
            }
            if (equal) {
                break;
            }
        }
        if (equal) {
            for (int i = s.length(); i > 0; i--) {
                for (int j = 0; j+i < s.length(); j++) {
                    return lengthOfLongestSubstring1(s.substring(j,i));
                }
            }
        }
        return temp.length;
    }

    public int[] towSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{nums[i],nums[j]};
                }
            }
        }
        return null;
    }


    @Autowired
    HistoryService historyService;

    @Test
    public void TestFindView() {
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId("2525").list();
        for (HistoricVariableInstance historicVariableInstance : list) {
            log.info("history = {}", historicVariableInstance.getValue());
        }

        List<HistoricTaskInstance> list1 = historyService.createHistoricTaskInstanceQuery().processInstanceId("2565").list();
        log.info("list1 size = {}", list1.size());
        for (HistoricTaskInstance historicTaskInstance : list1) {
            log.info("historyTask = {}", historicTaskInstance.getName());
            log.info("historyTask = {}", historicTaskInstance.getTaskDefinitionKey());
            Map<String, Object> processVariables = historicTaskInstance.getProcessVariables();
            for (String s : processVariables.keySet()) {
                log.info("s = {}", processVariables.get(s).toString());
            }
        }


        List<HistoricDetail> list2 = historyService.createHistoricDetailQuery().processInstanceId("7551").list();
        for (HistoricDetail historicDetail : list2) {
            Date time = historicDetail.getTime();
            String taskId = historicDetail.getTaskId();
            log.info("historyDetail = {}", historicDetail);
            log.info("time = {}, taskId = {}", time, taskId);
        }

//
//        List<HistoricVariableInstance> list3 = historyService.createHistoricVariableInstanceQuery().processInstanceId("7551").list();
//        for (HistoricVariableInstance historicVariableInstance : list3) {
//            log.info("historicVariableInstance = {}", historicVariableInstance.toString());
//            String variableName = historicVariableInstance.getVariableName();
//            Object value = historicVariableInstance.getValue();
//            log.info("variableName = {}, value = {}", variableName, value);
//
//        }
    }

    @Test
    public void TestHistorDetail() {
        List<HistoryDetailNode> historyDetailNodeList = new ArrayList<HistoryDetailNode>();

        List<HistoricDetail> list = historyService.createHistoricDetailQuery().processInstanceId("7551").orderByTime().asc().list();
        for (HistoricDetail historicDetail : list) {
            HistoricVariableUpdate variable = (HistoricVariableUpdate) historicDetail;

            String activityInstanceId = historicDetail.getActivityInstanceId();
            if (activityInstanceId != null) {
                HistoryDetailNode historyDetailNode = new HistoryDetailNode();

                historicDetail.getActivityInstanceId();


//                List<HistoricIdentityLink> historicIdentityLinksForTask = historyService.getHistoricIdentityLinksForTask()
//                for (HistoricIdentityLink historicIdentityLink : historicIdentityLinksForTask) {
//                    for (HistoricIdentityLink identityLink : historicIdentityLinksForTask) {
//                        System.out.println("identityLink: " + identityLink.getUserId());
//                    }
//                }
            }

            System.out.println("activityInstanceId: " + activityInstanceId);
            System.out.println("variable: " + variable.getVariableName() + " = " + variable.getValue());
        }
    }

    @Test
    public void TestTaskInstance() {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId("7551").list();
        for (HistoricTaskInstance historicTaskInstance : list) {
            log.info("historicTaskInstance = {}", JSON.toJSONString(historicTaskInstance));
        }

    }


    @Autowired
    UserService userService;
    @Test
    public void Testqqqq() {
        ReturnObject findUsers = userService.findUsers("findUsers");
        log.info("user = {}", findUsers);
    }
}

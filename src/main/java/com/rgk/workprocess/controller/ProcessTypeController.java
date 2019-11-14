package com.rgk.workprocess.controller;

import com.rgk.workprocess.domain.ReturnObject;
import com.rgk.workprocess.entity.MProcessType;
import com.rgk.workprocess.respository.IProcessTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prcessType")
public class ProcessTypeController {

    @Autowired
    IProcessTypeDao processTypeDao;

    @RequestMapping("/list")
    ReturnObject list() {
        List<MProcessType> all = processTypeDao.findAll();
        return new ReturnObject().setResult(all);
    }
}

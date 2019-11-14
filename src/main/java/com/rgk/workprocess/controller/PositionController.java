package com.rgk.workprocess.controller;

import com.rgk.workprocess.domain.ReturnObject;
import com.rgk.workprocess.remote.PositionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/position")
public class PositionController extends BaseController {

    @Autowired
    PositionService positionService;

    @RequestMapping(value = "/buildings")
    public ReturnObject getEngineers() {

//        String villageId = villageId();
//        log.info("villageId={}", villageId);
//        if (StringUtils.isEmpty(villageId)) {
//            villageId = "402820886c98ea1e016c98eefacd0002";
//        }
        String villageId = "402820886c98ea1e016c98eefacd0002";
        return positionService.findBuilding(villageId);
    }
}

package com.rgk.workprocess.controller;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.alibaba.fastjson.JSON;

public abstract class BaseController {
    protected Log log = LogFactory.getLog(getClass());


    @SuppressWarnings("unchecked")
    protected String villageId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication: " + JSON.toJSON(authentication));

        Map<String, Object> principal = (Map<String, Object>) authentication.getPrincipal();
        log.info("principal: " + JSON.toJSON(principal));

        Map<String, Object> details = (Map<String, Object>) principal.get("details");
        log.info("details: " + JSON.toJSON(details));

        String villageId = Objects.toString(details.get("villageId"),"");
        log.info("villageId: " + villageId);

        return villageId;
    }

    protected String userName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication: " + JSON.toJSON(authentication));
        return authentication.getName();
    }

}
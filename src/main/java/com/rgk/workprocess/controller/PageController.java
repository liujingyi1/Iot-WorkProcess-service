package com.rgk.workprocess.controller;

import com.rgk.workprocess.service.IPageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class PageController {

    @Autowired
    IPageService pageService;

    @GetMapping("/index.html")
    public String devices() {
        log.info("------ index -----");
        return "index";
    }

    @GetMapping("/deployment.html")
    public String deployment() {
        log.info("------ deployment.html -----");
        return "deployment.html";
    }

    @GetMapping("/new-process.html")
    public String newProcess() {
        log.info("------ new-process.html -----");
        return "new-process.html";
    }

    @GetMapping("/show-process.html")
    public String showProcess() {
        log.info("------ show-process.html -----");
        return "show-process.html";
    }

    @GetMapping("/handle-process.html")
    public String handleProcess(String id, Model model) {
        pageService.getProcessDetail(id, model);
        return "handle-process.html";
    }

    @GetMapping("/*")
    public String notFound() {
        log.info("------ 404.html -----");
        return "page-404.html";
    }
}

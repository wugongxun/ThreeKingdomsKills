package com.wgx.threekingdomskills.controller;

import com.wgx.threekingdomskills.bean.Message;
import com.wgx.threekingdomskills.service.GeneralService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * author:wgx
 * version:1.0
 */
@RestController
@RequestMapping("/tkk")
public class GeneralController {
    @Resource
    private GeneralService generalService;

    @GetMapping("/queryGenerals")
    public Message queryGenerals() {
        return generalService.queryGeneral();
    }
}

package com.danny.mq.rabbitmq.controller;

import com.danny.mq.rabbitmq.service.MqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/10下午5:09
 */
@RestController
public class BusinessController {
    @Autowired
    private MqService mqService;

    @GetMapping("/business/send")
    public String simpleSend(
            @RequestParam("message") String message,
            @RequestParam(value = "count", defaultValue = "1") int count) {
        mqService.sendMessage(message);
        return "success";
    }
}

package com.danny.mq.kafka.controller;

import com.alibaba.fastjson.JSON;
import com.danny.mq.kafka.config.KafkaConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2019/8/26下午1:34
 * SpringBoot整合kafka(实现producer和consumer) https://www.jianshu.com/p/5da86afed228
 */
@Slf4j
@RestController
public class KafkaController {

    @Value("${config.kafka.topic1}")
    private String kafkaTopic1;

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @GetMapping("/message/send")
    public boolean send(@RequestParam String message){
        kafkaTemplate.send(kafkaTopic1,message);
        log.info("kafka send message. topic[{}] message[{}]",KafkaConfig.TOPIC_01,message);
        return true;
    }

    @PostMapping("/stringparam")
    public String stringParamTest(@RequestBody String request){
        log.info("request:{}",request);
        StringParam stringParam= JSON.toJavaObject(JSON.parseObject(request),StringParam.class);
        return request;
    }

    @PostMapping("/jsonparam")
    public String jsonParamTest(@RequestBody StringParam request){
        log.info("request:{}",request);
        return JSON.toJSONString(request);
    }

    @Data
    public static class StringParam{
        private Integer id;
        private String name;
    }
}

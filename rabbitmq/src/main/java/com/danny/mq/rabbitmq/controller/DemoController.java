package com.danny.mq.rabbitmq.controller;

import com.danny.mq.rabbitmq.demo.amqp.exchange.direct.DirectSender;
import com.danny.mq.rabbitmq.demo.amqp.exchange.fanout.FanoutSender;
import com.danny.mq.rabbitmq.demo.amqp.exchange.topic.TopicSender;
import com.danny.mq.rabbitmq.demo.amqp.simple.SimpleSender;
import com.danny.mq.rabbitmq.demo.amqp.work.WorkSender;
import com.danny.mq.rabbitmq.utils.test.Executor;
import com.danny.mq.rabbitmq.utils.test.ExecutorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/2下午1:43
 */
@RestController
public class DemoController {

    @Autowired
    private SimpleSender simpleSender;

    @Autowired
    private WorkSender workSender;

    @Autowired
    private FanoutSender fanoutSender;

    @Autowired
    private DirectSender directSender;

    @Autowired
    private TopicSender topicSender;

    @GetMapping("/simple/send")
    public String simpleSend(
            @RequestParam("message") String message,
            @RequestParam(value = "count", defaultValue = "1") int count) {
        if (count == 1) {
            simpleSender.send(message);
        } else {
            new Executor(new ExecutorInterface() {
                @Override
                public void executeJob() {
                    simpleSender.send(message);
                }
            }).start(count);
        }
        return "success";
    }

    @GetMapping("/work/send")
    public String workSend(
            @RequestParam("message") String message,
            @RequestParam(value = "count", defaultValue = "1") int count) {
        if (count == 1) {
            workSender.send(message);
        } else {
            new Executor(new ExecutorInterface() {
                @Override
                public void executeJob() {
                    workSender.send(message);
                }
            }).start(count);
        }
        return "success";
    }

    @GetMapping("/fanout/send")
    public String fanoutSend(
            @RequestParam("message") String message,
            @RequestParam(value = "count", defaultValue = "1") int count) {
        if (count == 1) {
            fanoutSender.send(message);
        } else {
            new Executor(new ExecutorInterface() {
                @Override
                public void executeJob() {
                    fanoutSender.send(message);
                }
            }).start(count);
        }
        return "success";
    }

    @GetMapping("/direct/send")
    public String directSend(
            @RequestParam("routingKey") String routingKey,
            @RequestParam("message") String message,
            @RequestParam(value = "count", defaultValue = "1") int count,
            @RequestParam(value = "cycle", defaultValue = "1") int cycle) {
        if (count == 1) {
            directSender.send(routingKey, message);
        } else {
            while (true){
                new Executor(new ExecutorInterface() {
                    @Override
                    public void executeJob() {
                        directSender.send(routingKey, message);
                    }
                }).start(count);
            }
        }
        return "success";
    }

    @GetMapping("/topic/send")
    public String topicSend(
            @RequestParam("topic") String topic,
            @RequestParam("message") String message,
            @RequestParam(value = "count", defaultValue = "1") int count) {
        if (count == 1) {
            topicSender.send(topic, message);
        } else {
            new Executor(new ExecutorInterface() {
                @Override
                public void executeJob() {
                    topicSender.send(topic, message);
                }
            }).start(count);
        }
        return "success";
    }
}

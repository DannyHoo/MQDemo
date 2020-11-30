package com.danny.mq.rabbitmq.demo.amqp.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/2下午1:30
 */
@Component
@Slf4j
public class SimpleSender {

    @Autowired
    private RabbitTemplate simpleRabbitTemplate;

    public void send(String message) {
        simpleRabbitTemplate.convertAndSend(MqConfig.SIMPLE_QUEUE_NAME, message);
        log.info("SimpleSender send message:{}", message);
    }
}

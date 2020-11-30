package com.danny.mq.rabbitmq.demo.amqp.exchange.direct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/8下午4:28
 */
@Slf4j
@Component
public class DirectSender {

    @Autowired
    private RabbitTemplate directRabbitTemplate;

    public void send(String routingKey, String message) {
        directRabbitTemplate.convertAndSend(MqConfig.EXCHANGE, routingKey, message);
        log.info("SimpleSender send message:{}", message);
    }
}

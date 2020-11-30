package com.danny.mq.rabbitmq.demo.amqp.exchange.fanout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/7下午6:00
 */
@Slf4j
@Component
public class FanoutSender {

    @Autowired
    private RabbitTemplate fanoutRabbitTemplate;

    //把消费发给交换机，交换机再把消息同时转发到绑定的多个队列
    public void send(String message) {
        fanoutRabbitTemplate.convertAndSend(MqConfig.EXCHANGE,"",message);
        log.info("SimpleSender send message:{}", message);
    }
}

package com.danny.mq.rabbitmq.demo.amqp.work;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/6下午4:02
 */
@Component
@Slf4j
public class WorkSender {

    @Autowired
    private RabbitTemplate workRabbitTemplate;

    public void send(String message){
        workRabbitTemplate.convertAndSend(MqConfig.WORK_QUEUE_NAME,message);
        log.info("WorkSender send message:{}", message);
    }
}

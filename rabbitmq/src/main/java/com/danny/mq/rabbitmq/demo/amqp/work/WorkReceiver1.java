package com.danny.mq.rabbitmq.demo.amqp.work;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/2下午2:23
 */
@Slf4j
@Component
@RabbitListener(queues = MqConfig.WORK_QUEUE_NAME)
public class WorkReceiver1 {

    @RabbitHandler
    public void receive(String message) throws InterruptedException {
        Thread.sleep(100);
        log.info("WorkReceiver1 receive message:{}", message);
    }
}

package com.danny.mq.rabbitmq.demo.amqp.exchange.direct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/9上午10:28
 */
@Slf4j
@Component
public class DirectReceiver {

    @RabbitListener(queues = MqConfig.DIRECT_QUEUE1)
    public void receive1(String message) throws InterruptedException {
        log.info("DirectReceiver1 receive message:{}",message);
    }

    @RabbitListener(queues = MqConfig.DIRECT_QUEUE2)
    public void receive2(String message) throws InterruptedException {
        log.info("DirectReceiver2 receive message:{}",message);
    }

    @RabbitListener(queues = MqConfig.DIRECT_QUEUE3)
    public void receive3(String message) throws InterruptedException {
        log.info("DirectReceiver3 receive message:{}",message);
    }

}

package com.danny.mq.rabbitmq.demo.amqp.exchange.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/9上午11:38
 */
@Slf4j
@Component
public class TopicReceiver {

    @RabbitListener(queues = MqConfig.TOPIC_QUEUE1)
    public void receive1(String message) {
        log.info("TopicReceiver1 receive message:{}", message);
    }

    @RabbitListener(queues = MqConfig.TOPIC_QUEUE2)
    public void receive2(String message) {
        log.info("TopicReceiver2 receive message:{}", message);
    }

}

package com.danny.mq.rabbitmq.demo.amqp.exchange.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/9上午11:37
 */
@Slf4j
@Component
public class TopicSender {

    private final static String[] ENABLE_TOPICS = {
            "*.orange.*","abc.orange.def","abc.orange.hello world","hello world.orange.abc",
            "*.green.*","abc.green.def", "abc.green.你好 世界",
            "#.black.*.*","abc.def.black.ghi.lmn","abc.def.ghi.black.lmn.opq"};

    private final String[] DISABLE_TOPICS = {
            "abc.def.orange.ghi","ghi.orange.jkl.lmn",
            "abc.def.green.ghi","ghi.green.jkl.lmn",
            "hello world.black.abc", "abc.def.black.ghi", "black.abc"};

    @Autowired
    private RabbitTemplate topicRabbitTemplate;

    public void send(String topic, String message) {
        topicRabbitTemplate.convertAndSend(MqConfig.EXCHANGE, topic, message);
        log.info("TopicSender send message. topic: {} message:{}",topic, message);
    }

}

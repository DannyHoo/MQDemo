package com.danny.mq.rabbitmq.demo.amqp.simple;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/2上午11:43
 */
@Configuration("simpleMqConfit")
public class MqConfig {

    /*
     *
     * Sender----Queue1---Receiver1
     *
     */

    public final static String SIMPLE_QUEUE_NAME = "simple.queue";

    @Bean
    public Queue simpleQueue() {
        Queue simpleQueue = new Queue(SIMPLE_QUEUE_NAME);
        return simpleQueue;
    }

}

package com.danny.mq.rabbitmq.demo.amqp.work;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/2上午11:43
 */
@Configuration("workMqConfig")
public class MqConfig {

    /*
     *                    |--Receiver1
     * Sender-----Queue---|
     *                    |--Receiver2
     */

    public final static String WORK_QUEUE_NAME = "work.queue";

    @Bean
    public Queue workQueue() {
        Queue workQueue = new Queue(WORK_QUEUE_NAME);
        return workQueue;
    }

}

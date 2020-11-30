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
public class WorkReceiver2 {

    //无论消费者消费消息耗时多长，queue都会平均分配消息
    @RabbitHandler
    public void receive(String message) throws InterruptedException {
        Thread.sleep(5000);
        log.info("WorkReceiver2 receive message:{}", message);
    }
}

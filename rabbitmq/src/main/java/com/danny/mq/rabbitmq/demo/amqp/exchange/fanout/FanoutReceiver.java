package com.danny.mq.rabbitmq.demo.amqp.exchange.fanout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/8上午11:15
 *
 */
@Slf4j
@Component
public class FanoutReceiver {

    /**
     * 【RabbitMQ消费者配置】参考：@RabbitListener 与 @RabbitHandler 及 消息序列化： https://www.jianshu.com/p/911d987b5f11
     * 1、可以单独在方法上使用@RabbitListener，当监听到队列中有消息时则会进行接收并处理
     * 2、@RabbitListener 和 @RabbitHandler 搭配使用。@RabbitListener 可以标注在类上面，需配合 @RabbitHandler 注解一起使用。
     *   @RabbitListener  标注在类上面表示当有收到消息的时候，就交给 @RabbitHandler 的方法处理，具体使用哪个方法处理，根据 MessageConverter 转换后的参数类型
     */

    @Autowired
    private Queue fanoutQueue1;

    @Autowired
    private Queue fanoutQueue2;

    @RabbitListener(queues = "#{fanoutQueue1.name}")
    public void receive1(String message){
        log.info("FanoutReceiver1 receive message from {}:{}", fanoutQueue1.getName()+"/"+fanoutQueue1.getActualName(),message);
    }

    @RabbitListener(queues = "#{fanoutQueue2.name}")
    public void receive2(String message){
        log.info("FanoutReceiver2 receive message from {}:{}", fanoutQueue2.getName()+"/"+fanoutQueue2.getActualName(),message);
    }
}

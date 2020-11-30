package com.danny.mq.rabbitmq.demo.amqp.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/2下午1:40
 */
@Slf4j
@Component
@RabbitListener(queues = MqConfig.SIMPLE_QUEUE_NAME)
public class SimpleReceiver {

    /**
     * 【RabbitMQ消费者配置】参考：@RabbitListener 与 @RabbitHandler 及 消息序列化： https://www.jianshu.com/p/911d987b5f11
     * 1、可以单独在方法上使用@RabbitListener，当监听到队列中有消息时则会进行接收并处理
     * 2、@RabbitListener 和 @RabbitHandler 搭配使用。@RabbitListener 可以标注在类上面，需配合 @RabbitHandler 注解一起使用。
     *   @RabbitListener  标注在类上面表示当有收到消息的时候，就交给 @RabbitHandler 的方法处理，具体使用哪个方法处理，根据 MessageConverter 转换后的参数类型
     */

    @RabbitHandler
    public void receive(String message) throws Exception {
        Thread.sleep(500);
        //抛异常消费队列会重试发送消息
        /*if ("error".equals(message)) {
            log.info("SimpleReceiver receive message failed:{}", message);
            throw new Exception("receive failed");
        }*/
        log.info("SimpleReceiver receive message:{}", message);
    }
}

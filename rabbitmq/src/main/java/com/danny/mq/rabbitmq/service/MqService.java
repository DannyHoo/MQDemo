package com.danny.mq.rabbitmq.service;

import com.danny.mq.rabbitmq.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/10下午5:00
 */
@Slf4j
@Component
public class MqService {

    @Autowired
    private RabbitTemplate mqRabbitTemplate;

    public Boolean sendMessage(String message) {
        mqRabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.DIRECT_QUEUE1_ROUTING1, message);
        log.info("MqService sendMessage:{}", message);
        return Boolean.TRUE;
    }

    public Boolean sendMessage(String exchange, String routingKey, String payload) {
        try {
            //mqRabbitTemplate.convertAndSend(exchange, routingKey, payload);
            //发送消息时通过消息id绑定CorrelationData，发送到exchange失败回调时就可以获取到失败的消息内容
            String messageId= UUID.randomUUID().toString().replace("-","");
            Message message= MessageBuilder.withBody(payload.getBytes()).build();
            CorrelationData correlationData= new CorrelationData(messageId);
            correlationData.setReturnedMessage(message);
            mqRabbitTemplate.convertAndSend(exchange, routingKey, payload=messageId+payload,correlationData);
        } catch (Exception e) {
            //发送的时候，模拟断网，会抛异常：org.springframework.amqp.AmqpIOException: java.io.IOException
            //cause:com.rabbitmq.client.ShutdownSignalException: connection error
            log.info("MqService sendMessage error", e);
        }
        log.info("MqService sendMessage:{}", payload);
        return Boolean.TRUE;
    }

    private final static AtomicLong atomicLong = new AtomicLong(0);

    /**
     * RabbitMQ消费消息确认：
     * 消费消息手动确认机制分为三种：none、auto(默认)、manual
     * Auto：
     * 1. 如果消息成功被消费（成功的意思是在消费的过程中没有抛出异常），则自动确认
     * 2. 当抛出 AmqpRejectAndDontRequeueException 异常的时候，则消息会被拒绝，且 requeue = false（不重新入队列）
     * 3. 当抛出 ImmediateAcknowledgeAmqpException 异常，则消费者会被确认
     * 4. 其他的异常，则消息会被拒绝，且 requeue = true，此时会发生死循环，可以通过 setDefaultRequeueRejected（默认是true）去设置抛弃消息
     *
     * @param message
     * @throws Exception
     */
    @RabbitListener(queues = RabbitMqConfig.DIRECT_QUEUE1)
    public void onMessageQueue1(String message) throws Exception {
        log.info("MqService onMessageQueue1:{} count:{}", message, atomicLong.addAndGet(1));

        //模拟
        Thread.sleep(1000);
        if ("hello".equals(message)) {
            throw new Exception();
        }
    }

    @RabbitListener(queues = RabbitMqConfig.DIRECT_QUEUE2)
    public void onMessageQueue2(String message) throws Exception {
        log.info("MqService onMessageQueue2:{} ", message);
    }
}

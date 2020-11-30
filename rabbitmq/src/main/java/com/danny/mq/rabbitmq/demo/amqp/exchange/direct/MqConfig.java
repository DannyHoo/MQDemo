package com.danny.mq.rabbitmq.demo.amqp.exchange.direct;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/8下午3:27
 */
@Configuration("directMqConfig")
public class MqConfig {

    public final static String EXCHANGE="exchange.direct";

    public final static String DIRECT_QUEUE1="exchange.direct.queue1";
    public final static String DIRECT_QUEUE2="exchange.direct.queue2";
    public final static String DIRECT_QUEUE3="exchange.direct.queue3";

    public final static String DIRECT_QUEUE1_ROUTING1="orange";
    public final static String DIRECT_QUEUE2_ROUTING1="black";
    public final static String DIRECT_QUEUE2_ROUTING2="green";
    public final static String DIRECT_QUEUE3_ROUTING1="black";

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue directQueue1(){
        Queue queue=new Queue(DIRECT_QUEUE1,true);
        return queue;
    }

    @Bean
    public Queue directQueue2(){
        Queue queue=new Queue(DIRECT_QUEUE2,true);
        return queue;
    }

    @Bean
    public Queue directQueue3(){
        Queue queue=new Queue(DIRECT_QUEUE3,true);
        return queue;
    }

    @Bean
    public Binding directBindingQueue1Routing1(DirectExchange directExchange,Queue directQueue1){
        Binding binding= BindingBuilder.bind(directQueue1).to(directExchange).with(DIRECT_QUEUE1_ROUTING1);
        return binding;
    }

    // queue2和queue3和交换机的routingkey都是balck，
    // 当发送消息时如果指定路由键为black，则queue2和queu3都会受到该消息
    @Bean
    public Binding directBindingQueue2Routing1(DirectExchange directExchange,Queue directQueue2){
        Binding binding= BindingBuilder.bind(directQueue2).to(directExchange).with(DIRECT_QUEUE2_ROUTING1);
        return binding;
    }

    @Bean
    public Binding directBindingQueue2Routing2(DirectExchange directExchange,Queue directQueue2){
        Binding binding= BindingBuilder.bind(directQueue2).to(directExchange).with(DIRECT_QUEUE2_ROUTING2);
        return binding;
    }

    // queue2和queue3和交换机的routingkey都是balck，
    // 当发送消息时如果指定routingkey为black，则queue2和queu3都会受到该消息
    @Bean
    public Binding directBindingQueue3Routing1(DirectExchange directExchange,Queue directQueue3){
        Binding binding= BindingBuilder.bind(directQueue3).to(directExchange).with(DIRECT_QUEUE3_ROUTING1);
        return binding;
    }
}


package com.danny.mq.rabbitmq.demo.amqp.exchange.topic;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/9上午11:24
 */
@Configuration("topicMqConfig")
public class MqConfig {

    public final static String EXCHANGE = "exchange.topic";

    public final static String TOPIC_QUEUE1="exchange.topic.queue1";
    public final static String TOPIC_QUEUE2="exchange.topic.queue2";
    public final static String TOPIC_QUEUE3="exchange.topic.queue3";

    public final static String TOPIC_QUEUE1_TOPIC1="*.orange.*";
    public final static String TOPIC_QUEUE2_TOPIC1="*.green.*";
    public final static String TOPIC_QUEUE2_TOPIC2="#.black.*.*";

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue topicQueue1(){
        return new Queue(TOPIC_QUEUE1,true);
    }
    @Bean
    public Queue topicQueue2(){
        return new Queue(TOPIC_QUEUE2,true);
    }

    @Bean
    public Binding topicBindingQueue1Topic1(TopicExchange topicExchange,Queue topicQueue1){
        Binding binding= BindingBuilder.bind(topicQueue1).to(topicExchange).with(TOPIC_QUEUE1_TOPIC1);
        return binding;
    }

    @Bean
    public Binding topicBindingQueue2Topic1(TopicExchange topicExchange,Queue topicQueue2){
        Binding binding= BindingBuilder.bind(topicQueue2).to(topicExchange).with(TOPIC_QUEUE2_TOPIC1);
        return binding;
    }

    @Bean
    public Binding topicBindingQueue2Topic2(TopicExchange topicExchange,Queue topicQueue2){
        Binding binding= BindingBuilder.bind(topicQueue2).to(topicExchange).with(TOPIC_QUEUE2_TOPIC2);
        return binding;
    }

}

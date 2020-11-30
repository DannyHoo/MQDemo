package com.danny.mq.rabbitmq.demo.amqp.exchange.fanout;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/7下午6:00
 */
@Configuration("fanoutMqConfig")
public class MqConfig {

    /*
     *                      |--Queue1--Receiver1
     * Sender----Exchange---|
     *                      |--Queue2--Receiver2
     */

    public final static String EXCHANGE = "exchange.fanout";

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE);
    }

    //AnonymousQueue类型的队列，它的名字是由客户端生成的，而且是非持久的，独占的，自动删除的队列
    @Bean
    public Queue fanoutQueue1() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue fanoutQueue2() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding fanoutBinding1(FanoutExchange fanoutExchange, Queue fanoutQueue1) {
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    @Bean
    public Binding fanoutBinding2(FanoutExchange fanoutExchange, Queue fanoutQueue2) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }
}

package com.danny.mq.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 项目开发配置
 *
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/9下午3:56
 */
@Slf4j
@Configuration
public class RabbitMqConfig {

    /************************************************************************
     ************************** 业务队列配置 **********************************
     ************************************************************************/

    @Value("${spring.rabbitmq.host}")
    String host;
    @Value("${spring.rabbitmq.port}")
    Integer port;
    @Value("${spring.rabbitmq.username}")
    String userName;
    @Value("${spring.rabbitmq.password}")
    String password;
    @Value("${spring.rabbitmq.publisher-confirms}")
    Boolean publisherConfirms;
    @Value("${spring.rabbitmq.virtual-host}")
    String virtualHost;

    public final static String EXCHANGE = "demo.exchange.direct";

    public final static String DIRECT_QUEUE1 = "demo.exchange.direct.queue1";
    public final static String DIRECT_QUEUE2 = "demo.exchange.direct.queue2";

    public final static String DIRECT_QUEUE1_ROUTING1 = "orange";
    public final static String DIRECT_QUEUE2_ROUTING1 = "black";

    @Bean(name = "mqConnectionFactory")
    @Primary
    public ConnectionFactory mqConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirms(Boolean.TRUE);
        connectionFactory.setPublisherReturns(Boolean.TRUE);
        return connectionFactory;
    }

    /**
     * 《SpringBoot+RabbitMQ发送确认和消费手动确认机制》</>https://blog.csdn.net/yuyeqianhen/article/details/95065170
     * 《RabbitMQ(四)消息确认(发送确认,接收确认)》</>https://blog.csdn.net/qq315737546/article/details/54176560
     * 《RabbitMQ(五)消息发送失败后的处理》http://www.voidcn.com/article/p-uetnqqxw-ep.html
     * 《关于RabbitMq 生产者消息丢失问题》https://blog.csdn.net/dashuaigege642/article/details/100084935
     * 《RabbitMQ高级特性--消息如何保障100%的投递成功》https://www.jianshu.com/p/b177d9d1d704
     * @param mqConnectionFactory
     * @return
     */
    @Bean(name = "mqRabbitTemplate")
    @Primary
    public RabbitTemplate mqRabbitTemplate(@Qualifier("mqConnectionFactory") ConnectionFactory mqConnectionFactory) {
        RabbitTemplate mqRabbitTemplate = new RabbitTemplate(mqConnectionFactory);

        //exchange到queue失败时,开启回调return。如果为false，消息不可达时，broker会删除消息，不会触发回调
        mqRabbitTemplate.setMandatory(true);

        // ConfirmCallback 只确认消息是否正确到达 Exchange 中
        // 如果消息没有到exchange,则confirm回调,ack=false
        // 如果消息到达exchange,则confirm回调,ack=true
        mqRabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            log.info("confirmCallBack 确认情况：{}", ack);
            if (!ack) {
                log.info("confirmCallBack 发送失败的数据：{}", correlationData);
                log.info("confirmCallBack 发送失败的原因：{}", cause);
            }
        });

        // ReturnCallback   消息没有正确到达队列时触发回调，如果正确到达队列不执行
        // exchange到queue成功,则不回调return
        // exchange到queue失败,则回调return
        mqRabbitTemplate.setReturnCallback((Message message, int replyCode, String replyText, String exchange, String routingKey) -> {
            log.info("returnCallBack 消息未到达queue");
            log.info("returnCallBack 消息：{}", message);
            log.info("returnCallBack 回应码：{}", replyCode);
            log.info("returnCallBack 回应信息：{}", replyText);
            log.info("returnCallBack 交换机：{}", exchange);
            log.info("returnCallBack 路由键：{}", routingKey);
        });
        return mqRabbitTemplate;
    }

    @Bean(name = "mqContainerFactory")
    public SimpleRabbitListenerContainerFactory mqContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configure,
            @Qualifier("mqConnectionFactory") ConnectionFactory mqConnectionFactory) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        configure.configure(containerFactory, mqConnectionFactory);
        return containerFactory;
    }

    @Bean(name = "rabbitAdmin")
    public RabbitAdmin rabbitAdmin(@Qualifier("mqConnectionFactory") ConnectionFactory mqConnectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(mqConnectionFactory);

        DirectExchange directExchange1 = new DirectExchange(EXCHANGE, true, false);
        rabbitAdmin.declareExchange(directExchange1);

        Queue queue1 = new Queue(DIRECT_QUEUE1, true);
        Binding binding1 = BindingBuilder.bind(queue1).to(directExchange1).with(DIRECT_QUEUE1_ROUTING1);
        rabbitAdmin.declareBinding(binding1);

        /*Queue queue2 = new Queue(DIRECT_QUEUE2, true);
        Binding binding2 = BindingBuilder.bind(queue2).to(directExchange1).with(DIRECT_QUEUE2_ROUTING1);
        rabbitAdmin.declareBinding(binding2);*/

        return rabbitAdmin;
    }


    /************************************************************************
     ************************** 延迟队列配置 **********************************
     ************************************************************************/

    @Value("${spring.rabbitmq.host}")
    String delayedHost;
    @Value("${spring.rabbitmq.port}")
    Integer delayedPort;
    @Value("${spring.rabbitmq.username}")
    String delayedUserName;
    @Value("${spring.rabbitmq.password}")
    String delayedPassword;
    @Value("${spring.rabbitmq.publisher-confirms}")
    Boolean delayedPublisherConfirms;
    @Value("${spring.rabbitmq.virtual-host}")
    String delayedVirtualHost;
}

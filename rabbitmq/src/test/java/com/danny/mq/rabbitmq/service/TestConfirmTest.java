package com.danny.mq.rabbitmq.service;

import com.danny.mq.rabbitmq.RabbitmqDemoApplication;
import com.danny.mq.rabbitmq.RabbitmqDemoApplicationTests;
import com.danny.mq.rabbitmq.config.RabbitMqConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2020/7/20下午4:19
 * https://blog.csdn.net/qq315737546/article/details/54176560
 */
public class TestConfirmTest extends RabbitmqDemoApplicationTests {
    @Autowired
    private MqService mqService;

    private static String exChange = RabbitMqConfig.EXCHANGE;

    private static String routingKey = RabbitMqConfig.DIRECT_QUEUE1_ROUTING1;

    /**
     * exchange,queue 都正确,confirm被回调, ack=true
     *
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {
        String message = "currentTime:" + System.currentTimeMillis();
        System.out.println("test1---message:" + message);
        mqService.sendMessage(exChange, routingKey, message);
        Thread.sleep(1000);
    }

    /**
     * exchange 错误,queue 正确,confirm被回调, ack=false
     * channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'demo.exchange.directNO' in vhost 'ipos_cashback', class-id=60, method-id=40)
     *
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {
        String message = "currentTime:" + System.currentTimeMillis();
        System.out.println("test2---message:" + message);
        mqService.sendMessage(exChange + "NO", routingKey, message);
        Thread.sleep(1000);
    }

    /**
     * exchange 正确,queue 错误 ,confirm被回调, ack=true; return被回调 replyText:NO_ROUTE
     *
     * @throws InterruptedException
     */
    @Test
    public void test3() throws InterruptedException {
        String message = "currentTime:" + System.currentTimeMillis();
        System.out.println("test3---message:" + message);
        mqService.sendMessage(exChange, "", message);
        Thread.sleep(1000);
    }

    /**
     * exchange 错误,queue 错误,confirm被回调, ack=false
     *
     * @throws InterruptedException
     */
    @Test
    public void test4() throws InterruptedException {
        String message = "currentTime:" + System.currentTimeMillis();
        System.out.println("test4---message:" + message);
        mqService.sendMessage(exChange + "NO", routingKey, message);
        Thread.sleep(1000);
    }
}

package com.danny.mq.kafka.component;

import com.danny.mq.kafka.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author huyuyang
 * @email yuyang.hu@opay-inc.com
 * @date 2019/8/26下午1:35
 */
@Slf4j
@Component
public class KafkaConsumerLinstener {

    //@KafkaListener(topics= KafkaConfig.TOPIC_01)
    @KafkaListener(

    )
    public void onMessage(String message){
        log.info("kafka received message. topic[{}] message[{}]","kafkaTopic1",message);
    }

}

package com.fengxuechao.example.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

/**
 * {@link EnableBinding} 注解实现对消息通道的绑定<br>
 * {@link Sink} 是 Spring Cloud Stream 中默认实现的对输入消息通道的绑定的定义<br>
 * {@link StreamListener} 该注解表示该方法为消息中间件上数据流的事件监听器,
 * Sink.INPUT参数表示这是input消息通道上的监听处理器
 *
 * @author fengxuechao
 * @version 0.1
 * @date 2019/12/6
 */
@Slf4j
@EnableBinding({Sink.class})
public class SinkReceiver {

    /**
     * 消费消息
     *
     * @param message 消息
     */
    @StreamListener(Sink.INPUT)
    public void receive(Message<Object> message) {
        log.info("Received: headers = {}, payload = {}", message.getHeaders(), message.getPayload());
    }
}

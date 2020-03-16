package com.fengxuechao.examples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.scheduling.annotation.EnableScheduling;

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
@EnableScheduling
@EnableBinding({Sink.class})
public class SinkReceiver {

    /**
     * 消费消息
     *
     * @param message 消息
     */
    @StreamListener(Sink.INPUT)
    public void receive(Message<Object> message) {
        log.info("模拟收到消息处理失败后抛出异常");
        throw new RuntimeException("消息处理失败");
    }

    /**
     * 局部错误处理
     *
     * @param errorMessage
     */
    @ServiceActivator(inputChannel = "default-message.consumer-group.errors")
    public void localMessageErrorHandler(ErrorMessage errorMessage) {
        log.error("局部处理消息异常");
        Throwable throwable = errorMessage.getPayload();
        log.error("捕获异常 - {}", throwable.getMessage());

        MessageHeaders headers = errorMessage.getHeaders();
        log.error("错误消息头 - {}", headers);
    }


    /**
     * 全局错误处理
     * <p>
     * 针对 topic 为 default-message, 消费者分组为 consumer-group 的消息错误处理通道
     *
     * @param errorMessage 错误消息
     */
    @StreamListener("errorChannel")
    public void globalMessageErrorHandler(ErrorMessage errorMessage) {
        log.error("全局处理消息异常");
        Throwable throwable = errorMessage.getPayload();
        log.error("捕获异常 - {}", throwable.getMessage());

        MessageHeaders headers = errorMessage.getHeaders();
        log.error("错误消息头 - {}", headers);
    }
}

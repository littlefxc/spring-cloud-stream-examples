package com.fengxuechao.example.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;

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
@EnableBinding({Sink.class, Source.class})
public class SinkReceiver {

    @Autowired
    private Source source;

    /**
     * 要指定输出通道
     */
    @Autowired
    @Qualifier("output")
    private MessageChannel messageChannel;

    /**
     * 消费消息
     *
     * @param payload
     */
    @StreamListener(Sink.INPUT)
    public void receive(@Payload String payload) {
        log.info("Received:{}", payload);
    }

    /**
     * 支持企业消息集成
     * 处理消息
     *
     * @param message
     * @return
     */
    @Transformer(inputChannel = Sink.INPUT, outputChannel = Source.OUTPUT)
    public Object transform(String message) {
        return message.toUpperCase();
    }

    /**
     * 生产消息
     */
    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        log.info("定时发送消息");
        // 使用 Source 和 MessageChannel 可以达到同样的效果
        source.output().send(MessageBuilder.withPayload("hello, 123").build());
//        messageChannel.send(MessageBuilder.withPayload("hello, 123").build());
    }
}

package com.fengxuechao.example.rabbit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengxuechao
 * @version 0.1
 * @date 2019/12/25
 */
@EnableBinding({Source.class})
@RestController
public class WebController {

    /**
     * 要指定输出通道
     */
    @Autowired
    @Qualifier(Source.OUTPUT)
    private MessageChannel messageChannel;

    @Autowired
    private Source source;

    @GetMapping("/messageChannel")
    public void messageChannel() {
        messageChannel.send(MessageBuilder.withPayload("使用 MessageChannel 发送消息").build());
    }

    @GetMapping("/source")
    public void source() {
//        source.output().send(MessageBuilder.withPayload("使用 Source 发送消息").build());
        source.output().send(
                MessageBuilder
                        .withPayload("使用 Source 发送消息")
                        .setHeader("test", true)
                        .build());
    }
}

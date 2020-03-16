package com.fengxuechao.examples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
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

    @Autowired
    private Source source;

    /**
     * 发送消息
     */
    @GetMapping("/source")
    public void source() {
        source.output().send(MessageBuilder.withPayload("使用 Source 发送消息").build());
    }


}

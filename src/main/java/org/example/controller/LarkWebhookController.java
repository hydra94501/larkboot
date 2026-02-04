package org.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class LarkWebhookController {

    private static final Logger logger = LoggerFactory.getLogger(LarkWebhookController.class);

    @PostMapping("/lark")
    public ResponseEntity<?> handleLarkWebhook(@RequestBody Map<String, Object> body) {
        logger.info("Received Lark Webhook Body: {}", body);

        // 检查是否开启了 Encrypt Key
        if (body.containsKey("encrypt")) {
            logger.error("收到加密的请求！当前代码不支持解密。请在飞书开发者后台 -> 事件订阅 -> 配置 -> Encrypt Key 中清空密钥，或者实现解密逻辑。");
            return ResponseEntity.ok().body(Collections.singletonMap("msg", "encryption not supported, please disable encrypt key"));
        }

        // 处理飞书 URL 验证校验
        if (body.containsKey("challenge") && "url_verification".equals(body.get("type"))) {
            String challenge = (String) body.get("challenge");
            logger.info("Responding to URL verification challenge: {}", challenge);
            return ResponseEntity.ok(Collections.singletonMap("challenge", challenge));
        }

        // 处理其他事件回调
        return ResponseEntity.ok("success");
    }
}

# Lark Webhook Receiver

这是一个简单的 Spring Boot 应用程序，用于接收飞书 (Lark) Webhook 回调并打印内容。

## 环境要求

- JDK 1.8 或更高版本
- Maven 3.x

## 构建与运行

1.  **打包项目**：
    在项目根目录下运行：
    ```bash
    mvn clean package
    ```

2.  **运行应用**：
    构建成功后，运行生成的 JAR 包：
    ```bash
    java -jar target/larkboot-1.0-SNAPSHOT.jar
    ```

## 接口说明

- **URL**: `/webhook/lark`
- **Method**: POST
- **Content-Type**: application/json

该接口会自动处理飞书的 `url_verification` 挑战验证，并打印接收到的所有事件内容到控制台日志。

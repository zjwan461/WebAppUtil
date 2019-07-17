package com.itsu.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author 苏犇
 * @date 2019/7/9 22:41
 */

public class RabbitMQUtil {

    private static ConnectionFactory connectionFactory = new ConnectionFactory();

    public static Connection getConnection(String host, String userName, String password, String virtualHost, int port) {
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setHost(host);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setPort(port);

        try {
            Connection connection = connectionFactory.newConnection();
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

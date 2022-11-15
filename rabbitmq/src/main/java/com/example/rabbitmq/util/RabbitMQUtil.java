package com.example.rabbitmq.util;

public final class RabbitMQUtil {

    private static final String EXCHANGE ="our-exchange";
    private static final String ROUTING_KEY ="routing_key";
    private static final String TYPE ="direct";
    private static final String QUEUE ="our-queue";


    private RabbitMQUtil(){
    }

    public static String getExchange() {
        return EXCHANGE;
    }

    public static String getType() {
        return TYPE;
    }

    public static String getRoutingKey() {
        return ROUTING_KEY;
    }

    public static String getQueue() { return QUEUE; }
}

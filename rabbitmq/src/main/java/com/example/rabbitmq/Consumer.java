package com.example.rabbitmq;


import com.example.rabbitmq.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Component
public class Consumer {
    private static final Logger LOG= LoggerFactory.getLogger(Consumer.class);

    @PostConstruct
    private void init(){
        listenForMessage();
    }

    private void listenForMessage(){
        try {
            final ConnectionFactory factory=new ConnectionFactory();
            final Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.queueDeclare(
                    RabbitMQUtil.getQueue(),
                    false,//durable (fiziksel mi)
                    false,//diger connectionlar ile kulllanılması
                    false,//autodelete
                    null);//exchange
            channel.queueBind(// exchange i kuyruğa bagla
                    RabbitMQUtil.getQueue(),
                    RabbitMQUtil.getExchange(),
                    RabbitMQUtil.getRoutingKey());



            channel.basicConsume(//queue den mesaj cekme islemi
                    RabbitMQUtil.getQueue(),
                    false,//mesajı siler
                    ((consumerTag, message)->LOG.info("Mesajiniz var.'{}' ",new String(message.getBody(), StandardCharsets.UTF_8))),
                    ((consumerTag, sig)-> LOG.error(sig.getMessage())));

        }catch (final Exception e){
            LOG.error(e.getMessage(),e);
        }

    }
}

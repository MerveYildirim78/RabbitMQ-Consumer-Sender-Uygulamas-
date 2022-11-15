package com.example.rabbitmq;



import com.example.rabbitmq.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;


@Component
public class Sender {
    private static final Logger LOG= LoggerFactory.getLogger(Sender.class);
    @PostConstruct
    private void init(){
       final Scanner scanner = new Scanner(System.in);
       askForMessage(scanner);
    }
    private void SendMessage(final String message){
       final ConnectionFactory factory=new ConnectionFactory();//rabbitmq hostuna baglanmak icin kullanılır

        try (final Connection connection = factory.newConnection()) {

            final Channel channel = connection.createChannel(); //mesajlarımızı göndereceğimiz rabbitmq üzerinde kanal
            channel.exchangeDeclare(
                    RabbitMQUtil.getExchange(),
                    RabbitMQUtil.getType());
            channel.basicPublish(//mesajın hangi kuyruga konulması
                    RabbitMQUtil.getExchange(),
                    RabbitMQUtil.getRoutingKey(),
                    false,
                    null,
                    message.getBytes());//mesajı bytelar ile gönderiyoruz

        } catch (IOException | TimeoutException e) {
            LOG.error(e.getMessage(),e);
        }

    }

    private void askForMessage(final Scanner scanner){
        LOG.info("Mesajinizi girebilirsiniz : ");
        final String message = scanner.nextLine()+ LocalDateTime.now();
        SendMessage(message);
        askForMessage(scanner);

    }


}

package alotor.rabbit

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.beans.factory.annotation.Value

import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate

@Configuration
public class RabbitMQConfig {
    @Value('${rabbitmq.host}')
    String host

    @Value('${rabbitmq.port}')
    Integer port

    @Value('${rabbitmq.username}')
    String username

    @Value('${rabbitmq.password}')
    String password

    @Bean
    public ConnectionFactory connectionFactory() {
        def connectionFactory = new CachingConnectionFactory(this.host, this.port)
        connectionFactory.username = this.username
        connectionFactory.password = this.password
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate amqpTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue requestQueue() {
       return new Queue("request");
    }
}
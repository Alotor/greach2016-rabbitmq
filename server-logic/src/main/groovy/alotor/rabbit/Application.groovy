package alotor.rabbit

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.integration.config.EnableIntegration

import org.springframework.context.annotation.Bean
import org.springframework.messaging.MessageChannel
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.dsl.amqp.Amqp
import org.springframework.integration.annotation.IntegrationComponentScan
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.core.AmqpTemplate

@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
public class Application {

    static void main(String[] args) {
        SpringApplication.run Application, args
    }

    @Bean
    public MessageChannel responseChannel() {
        new DirectChannel()
    }

    @Bean
    JsonSerializer jsonSerializer() {
        new JsonSerializer()
    }

    @Bean
    public IntegrationFlow amqpInboundGateway(
        ConnectionFactory connectionFactory,
        JsonSerializer jsonSerializer,
        MainService mainService) {

        IntegrationFlows
            .from(Amqp.inboundAdapter(connectionFactory, "request"))
            .transform(jsonSerializer, "fromJson")
            .handle(mainService, "startTopic")
            .get();
    }

    @Bean
    public IntegrationFlow amqpOutboundGateway(
        AmqpTemplate amqpTemplate,
        JsonSerializer jsonSerializer) {
        IntegrationFlows
            .from("responseChannel")
            .transform(jsonSerializer, "toJson")
            .handle(Amqp.outboundAdapter(amqpTemplate)
                        .exchangeName("amq.topic")
                        .routingKeyExpression("headers.routingKey"))
            .get()
    }

}

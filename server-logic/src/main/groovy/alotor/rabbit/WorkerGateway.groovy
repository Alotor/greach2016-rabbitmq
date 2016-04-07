package alotor.rabbit

import org.springframework.integration.annotation.MessagingGateway
import org.springframework.integration.annotation.Header
import org.springframework.integration.annotation.Payload

@MessagingGateway(defaultRequestChannel = "responseChannel")
interface WorkerGateway {
    void broadcastMessage(@Header("routingKey") String topic, @Payload Map message)
}
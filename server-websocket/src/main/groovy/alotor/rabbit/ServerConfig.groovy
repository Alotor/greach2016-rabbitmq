package alotor.rabbit

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(value = 'websockets.relay')
@Component
class ServerConfig {
    String host
    Integer port
    String username
    String password
    String stompEndpoint
    String allowedOrigins
}
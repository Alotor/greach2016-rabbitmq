package alotor.rabbit

import org.springframework.beans.factory.annotation.Autowired

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.simp.config.StompBrokerRelayRegistration

import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry

/*
<websocket:message-broker applicationDestinationPrefix="/app">
  <websocket:stomp-endpoint path="/stomp">
    <websocket:sockjs />
    <websocket:handshake-handler ref="myHandler" />
  </websocket:stomp-endpoint>
  <websocket:simple-broker prefix="/topic" />
</websocket:message-broker>

<websocket:message-broker>
  <websocket:stomp-endpoint path="/foo">
    <websocket:sockjs />
    <websocket:handshake-handler ref="myHandler" />
  </websocket:stomp-endpoint>
  <websocket:stomp-broker-relay prefix="/topic,/queue" relayHost=".." relayPort=".."  etc />
</websocket:message-broker>
*/
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer { // <1>

    @Autowired
    ServerConfig serverConfig

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        def brokerRelay = config.enableStompBrokerRelay("/queue", "/topic")

        brokerRelay.relayHost = serverConfig.host
        brokerRelay.relayPort = serverConfig.port

        // Credentials
        brokerRelay.systemLogin = serverConfig.username
        brokerRelay.systemPasscode = serverConfig.password
        brokerRelay.clientLogin = serverConfig.username
        brokerRelay.clientPasscode = serverConfig.password
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
            .addEndpoint(serverConfig.stompEndpoint)
            .setAllowedOrigins(serverConfig.allowedOrigins)
    }

}
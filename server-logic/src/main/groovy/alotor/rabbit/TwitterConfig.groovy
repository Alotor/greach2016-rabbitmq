package alotor.rabbit

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(value = 'twitter')
@Component
class TwitterConfig {
    String consumerKey
    String consumerSecret
    String token
    String secret
}
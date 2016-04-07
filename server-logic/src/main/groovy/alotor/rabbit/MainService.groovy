package alotor.rabbit

import groovy.json.JsonSlurper

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value

import com.twitter.hbc.httpclient.auth.OAuth1

@Service
class MainService {
    @Value('${twitter.consumerKey}')
    String consumerKey

    @Value('${twitter.consumerSecret}')
    String consumerSecret

    @Value('${twitter.token}')
    String token

    @Value('${twitter.secret}')
    String secret

    @Autowired
    WorkerGateway workerGateway

    @Autowired
    JsonSerializer jsonSerializer

    public void startTopic(Map message) {
        try {
            new TwitterWorker(auth, message.topic).fetchTweets {
                String topic, String json ->
                def tweet = jsonSerializer.fromJson(json.bytes)
                workerGateway.broadcastMessage(topic, tweet)
            }
        } catch(e) {
            // We wan't to capture the exception so it acks rabbit
            // otherwise enters an infinite-loop
            e.printStackTrace()
        }
    }

    OAuth1 getAuth() {
        new OAuth1(
            this.consumerKey,
            this.consumerSecret,
            this.token,
            this.secret
        )
    }
}
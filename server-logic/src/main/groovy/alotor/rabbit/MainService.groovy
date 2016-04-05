package alotor.rabbit

import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired

import com.twitter.hbc.httpclient.auth.OAuth1

@Service
@Slf4j
class MainService {
    @Autowired
    WorkerGateway workerGateway

    @Autowired
    TwitterConfig twitterConfig

    public void startTopic(Map message) {
        log.debug ">>> Start topic $message"

        try {
            def worker = new TwitterWorker(auth, message.topic, this.&notify)
            new Thread(worker).start()
        } catch(e) {
            e.printStackTrace()
        }
    }

    OAuth1 getAuth() {
        new OAuth1(
            twitterConfig.consumerKey,
            twitterConfig.consumerSecret,
            twitterConfig.token,
            twitterConfig.secret
        )
    }

    void notify(String topic, String message) {
        def tweet = new JsonSlurper().parseText(message)
        log.debug ">>> @${tweet.user.screen_name}${tweet.text}"
        workerGateway.broadcastMessage(topic, tweet)
    }
}
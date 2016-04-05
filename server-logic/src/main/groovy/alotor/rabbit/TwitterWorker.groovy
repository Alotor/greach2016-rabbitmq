package alotor.rabbit

import groovy.transform.TupleConstructor
import java.util.concurrent.LinkedBlockingQueue;

import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.httpclient.auth.OAuth1
import com.twitter.hbc.core.Constants
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint
import com.twitter.hbc.core.processor.StringDelimitedProcessor

@TupleConstructor
class TwitterWorker implements Runnable {
    OAuth1 auth
    String topic
    Closure onNewTopic

    public void run() {
        def queue = new LinkedBlockingQueue<String>(10000)
        def endpoint = new StatusesFilterEndpoint()

        endpoint.trackTerms([ "#${this.topic}" ])

        def client = new ClientBuilder()
            .hosts(Constants.STREAM_HOST)
            .endpoint(endpoint)
            .authentication(auth)
            .processor(new StringDelimitedProcessor(queue))
            .build()

        client.connect()

        for (int msgRead = 0; msgRead < 100; msgRead++) {
            this.onNewTopic(this.topic, queue.take())
            Thread.sleep(750)
        }

        client.stop()
    }
}
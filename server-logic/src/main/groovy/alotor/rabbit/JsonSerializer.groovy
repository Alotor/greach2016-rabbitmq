package alotor.rabbit

import groovy.json.JsonSlurper
import groovy.json.JsonOutput

class JsonSerializer {
    byte[] toJson(Map payload) {
        return JsonOutput.toJson(payload).bytes
    }

    Map fromJson(byte[] payload) {
        return new JsonSlurper().parse(payload)
    }

}